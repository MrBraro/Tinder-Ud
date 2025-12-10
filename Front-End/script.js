/**
 * script.js - Lógica principal del frontend de Tinder UD
 * 
 * Gestiona autenticación, perfil de usuario, swipes, matches y chat.
 * Integra con los microservicios backend mediante REST API.
 * 
 * @author Paula Martinez (Diseño original)
 * @modified Juan Estevan Ariza Ortiza (Arreglo de apartados)
 * @modified Juan Sebastián Bravo Rojas (Refactorización completa y correcciones)
 * @version 2.0
 * @since 2025-12-09
 */

// API ENDPOINTS
const AUTH_API = 'http://localhost:8081/auth';
const USER_API = 'http://localhost:8082/usuario';
const SEG_API = 'http://localhost:8083/seguidor';
const SWIPE_API = 'http://localhost:8084/swipe';
const MATCH_API = 'http://localhost:8085/match';

document.addEventListener('DOMContentLoaded', async () => {

    // checkAuth(); // Removed: Function no longer exists, logic moved inline.
    console.log("Script loaded. Initializing...");
    const path = window.location.pathname;

    // Universal Data Sync (Header & Common Elements) - FETCH USER FIRST!
    // This ensures we have the correct ID (via Email lookup) before init components.
    await fetchUserProfile();

    // --- COMPONENT INITIALIZATION BASED ON ELEMENTS ---

    // 1. Auth Modal (Index Page)
    if (document.querySelector('.login-btn') || document.getElementById('createAccountBtn')) {
        initAuthModalForIndex();
    }

    // 2. Onboarding Wizard
    if (document.getElementById('profileWizardForm')) {
        initOnboardingLogic();
    }

    // 3. Recs Stack (Main App)
    if (document.getElementById('cardStack')) {
        initRecsLogic();
    }

    // 4. Matches List
    if (document.querySelector('.matches-list-panel') || document.querySelector('.messages-list')) {
        initMatchesLogic();
    }

    // 5. Profile Edit
    if (path.includes('profile.html')) {
        initProfileLogic();
    }
});


// LOGIN FORM
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const email = document.getElementById('loginEmail').value;
        const password = document.getElementById('loginPassword').value;

        try {
            const response = await fetch(`${AUTH_API}/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                const data = await response.json();

                // Clear old session
                localStorage.clear();

                // Update Context with correct field 'userId'
                UserContext.set({
                    id: data.userId, // Backend sends 'userId'
                    email: data.email,
                    token: data.token
                });

                window.location.href = "recs.html";
            } else {
                const errorMsg = await response.text();
                alert('Inicio de sesión fallido: ' + errorMsg);
            }
        } catch (err) {
            console.error(err);
            alert('Error de inicio de sesión: ' + err.message);
        }
    });
}

// REGISTER FORM
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const submitBtn = document.getElementById('regSubmitBtn');
        const email = document.getElementById('regEmail').value;
        const password = document.getElementById('regPassword').value;
        const confirm = document.getElementById('regConfirmPassword').value;

        if (password !== confirm) {
            alert("Passwords do not match");
            return;
        }

        if (submitBtn) {
            submitBtn.disabled = true;
            submitBtn.innerText = "Creando cuenta...";
        }

        try {
            const response = await fetch(`${AUTH_API}/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            });

            if (response.ok) {
                const data = await response.json();

                // Update Context
                UserContext.set({
                    id: data.userId, // Backend sends 'userId'
                    email: data.email
                });

                alert("¡Cuenta creada! Por favor completa tu perfil.");
                window.location.href = "onboarding.html";
            } else {
                const errorMsg = await response.text();
                alert('El registro falló: ' + errorMsg);
                if (submitBtn) {
                    submitBtn.disabled = false;
                    submitBtn.innerText = "CREAR CUENTA";
                }
            }
        } catch (err) {
            console.error(err);
            alert('Error en el registro: ' + err.message);
            if (submitBtn) {
                submitBtn.disabled = false;
                submitBtn.innerText = "CREAR CUENTA";
            }
        }
    });
}

// ONBOARDING PAGE
if (document.getElementById('profileWizardForm')) {
    // initOnboardingLogic(); // This is now called from the main DOMContentLoaded listener
}

// RECS PAGE
if (document.getElementById('cardStack')) {
    // initRecsLogic(); // This is now called from the main DOMContentLoaded listener
}

// SIDEBAR MATCHES
const sidebarMatches = document.getElementById('sidebarMatches');
if (sidebarMatches) {
    loadMatches(sidebarMatches);
}

// AUTH MODAL LOGIC (Existing UI Logic)
const authModal = document.getElementById('authModal');
if (authModal) {
    // initAuthModalForIndex(); // This is now called from the main DOMContentLoaded listener
}


function initOnboardingLogic() {
    let currentStep = 1;
    const totalSteps = 4;
    const nextBtn = document.getElementById('nextStepBtn');
    const progressBar = document.getElementById('wizardProgress');

    if (!nextBtn) return;

    nextBtn.addEventListener('click', async () => {
    // VALIDACIONES ANTES DE AVANZAR
    if (currentStep === 1) {
        const name = document.getElementById('profileName').value;
        if (!name.trim()) {
            alert("Por favor ingresa tu nombre");
            return; // Detiene la ejecución aquí
        }
    }

    if (currentStep === 2) {
        const day = document.getElementById('dobDay').value;
        const month = document.getElementById('dobMonth').value;
        const year = document.getElementById('dobYear').value;
        
        if (!day || !month || !year) {
            alert("Por favor completa tu fecha de nacimiento");
            return;
        }
        
        const age = 2025 - parseInt(year);
        if (age < 18) {
            alert("Debes ser mayor de 18 años");
            return;
        }
    }

    if (currentStep === 3) {
        const genderInput = document.querySelector('input[name="gender"]:checked');
        if (!genderInput) {
            alert("Por favor selecciona tu género");
            return;
        }
    }

    // AVANZAR AL SIGUIENTE PASO
    if (currentStep < totalSteps) {
        moveStep(++currentStep);
        updateProgress(progressBar, currentStep, totalSteps);
        if (currentStep === totalSteps) {
            nextBtn.innerText = "FINALIZAR";
        }
    } else {
        // Estamos en el último paso (4) - Finalizar
        if (uploadedPhotosCount < 2) {
            alert("Por favor sube al menos 2 fotos.");
            return;
        }

        nextBtn.disabled = true;
        nextBtn.innerText = "Guardando...";

        try {
            await saveProfile();
        } catch (e) {
            console.error(e);
            alert("Error técnico al guardar. Intenta de nuevo.");
            nextBtn.disabled = false;
            nextBtn.innerText = "FINALIZAR";
        }
    }
});
}

function moveStep(step) {
    document.querySelectorAll('.wizard-step').forEach(s => s.classList.remove('active-step'));
    document.querySelector(`.wizard-step[data-step="${step}"]`).classList.add('active-step');
}

function updateProgress(bar, step, total) {
    bar.style.width = `${(step / total) * 100}%`;
}

async function saveProfile() {
    const userId = localStorage.getItem('userId');
    const email = localStorage.getItem('userEmail');
    const nombre = document.getElementById('profileName')?.value || "User";
    const edad = parseInt(document.getElementById('dobYear')?.value) ? (2025 - parseInt(document.getElementById('dobYear').value)) : 22; // Approx age calculation

    // Get Gender
    const genderInput = document.querySelector('input[name="gender"]:checked');
    const genero = genderInput ? genderInput.value : "Otro";

    // Get Photo (First one)
    let fotoUrl = "https://via.placeholder.com/150";
    const firstSlot = document.querySelector('.photo-slot.has-photo');
    if (firstSlot) {
        // extract url('...')
        const bg = firstSlot.style.backgroundImage;
        if (bg && bg.startsWith('url')) {
            fotoUrl = bg.slice(5, -2); // Remove url(" and ")
        }
    }

    if (!email || email === "undefined" || email === "null") {
        alert("Error crítico: Email inválido (undefined). Por favor inicia sesión nuevamente.");
        localStorage.removeItem('userEmail'); // Clear bad data
        localStorage.removeItem('userId');
        window.location.href = "index.html";
        return;
    }

    // Robust Nickname
    const randomSuffix = Math.floor(Math.random() * 10000);
    const baseName = (nombre && nombre !== "undefined") ? nombre.replace(/\s/g, '') : email.split('@')[0];
    const finalNickname = `${baseName}${randomSuffix}`;

    const profileData = {
        // id: userId, 
        nombre: nombre || "Usuario",
        apellidos: "Usuario",
        nickname: finalNickname,
        email: email,
        edad: edad,
        genero: genero,
        ciudad: "Bogotá",
        descripcion: "¡Nuevo en Tinder UD!",
        fotoUrl: fotoUrl
    };

    try {
        const response = await fetch(USER_API, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(profileData)
        });

        if (response.ok) {
            const newUser = await response.json();
            localStorage.setItem('userId', newUser.id);
            localStorage.setItem('userPhoto', newUser.fotoUrl || "https://via.placeholder.com/150");
            alert("¡Perfil guardado!");
            window.location.href = "recs.html";
        } else {
            const errorText = await response.text();
            // Handle Race Condition: If profile already exists (Duplicate entry), treat as success
            if (errorText.includes("Duplicate entry")) {
                console.warn("Profile already exists (Race Condition detected). Redirecting...");
                // Ideally update local storage if possible, but we might not have the new ID.
                // Assuming previous success set it, or we continue with Auth ID.
                window.location.href = "recs.html";
                return;
            }
            throw new Error(errorText);
        }
    } catch (err) {
        console.error("Save Profile Error:", err);
        if (err.message.includes("Duplicate entry")) {
            window.location.href = "recs.html";
        } else {
            alert("Error al guardar perfil: " + err.message);
            // Re-throw to re-enable button
            throw err;
        }
    }
}


async function initRecsLogic() {
    const stack = document.getElementById('cardStack');
    // const userId = localStorage.getItem('userId');
    const user = UserContext.get();
    if (!stack || !user.id) return;

    let users = [];
    let currentIndex = 0;

    try {
        // Updated: Fetch ALL users and filter client-side since /recomendados endpoint is missing in Backend
        const res = await fetch(`${USER_API}`); // GET /usuario lists all
        if (res.ok) {
            const allUsers = await res.json();
            // Filter out current user
            users = allUsers.filter(u => u.id != user.id);
            console.log("Recommendations loaded (filtered):", users.length);
        } else {
            console.warn("Failed to load recommendations");
        }
    } catch (e) {
        console.error("Error loading recs", e);
    }

    // Function to render the current card
    function renderCard() {
        stack.innerHTML = '';
        if (currentIndex >= users.length) {
            // No more profiles
            stack.innerHTML = `
                <div style="text-align:center; padding-top:50%; color:#555;">
                    <h3>No hay más personas cerca</h3>
                    <p>Vuelve más tarde para ver nuevos perfiles.</p>
                </div>`;
            return;
        }

        const candidate = users[currentIndex];

        // Ensure photo is valid
        const imgUrl = (candidate.fotoUrl && (candidate.fotoUrl.startsWith('http') || candidate.fotoUrl.startsWith('data:image')))
            ? candidate.fotoUrl
            : 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?fit=crop&w=400';

        const card = document.createElement('div');
        card.className = 'tinder-card';
        card.style.backgroundImage = `url('${imgUrl}')`;

        // Calculate Age if needed, or use raw if provided
        const age = candidate.edad || 20;

        card.innerHTML = `
            <div class="card-info">
                <h3>${candidate.nombre || 'Usuario'}, ${age}</h3>
                <p>${candidate.descripcion || 'Sin biografía'}</p>
                <!-- Optional: Show distance if available -->
            </div>
        `;
        stack.appendChild(card);
    }

    renderCard();

    // Wire up the bottom control bar buttons
    const likeBtn = document.getElementById('likeBtn');
    const nopeBtn = document.getElementById('nopeBtn');

    if (likeBtn) likeBtn.onclick = () => window.handleSwipeAction(true);
    if (nopeBtn) nopeBtn.onclick = () => window.handleSwipeAction(false);

    // Global handler for buttons (since onclick strings need global access)
    // In a module, we'd attach listeners dynamically, but for simplicity:
    window.handleSwipeAction = async (isLike) => {
        const candidate = users[currentIndex];
        const card = document.querySelector('.tinder-card');

        // 1. Visual Animation
        if (card) {
            card.style.transition = 'transform 0.4s ease, opacity 0.4s';
            card.style.opacity = '0';
            card.style.transform = isLike
                ? 'translateX(200px) rotate(20deg)'
                : 'translateX(-200px) rotate(-20deg)';
        }

        // 2. Network Request
        try {
            // SwipeDTO structure based on Controller
            const swipeData = {
                idOrigen: user.id,
                idDestino: candidate.id,
                esLike: isLike
            };

            // Correcting endpoint to match Backend Controller @PostMapping("/swipe")
            const endpoint = SWIPE_API; // http://localhost:8084/swipe

            const realRes = await fetch(endpoint, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(swipeData)
            });

            if (realRes.ok) {
                const data = await realRes.json();
                console.log("Swipe Result DTO:", data); // DEBUG

                // Robust check for boolean field (Jackson might serialize as 'match' or 'isMatch')
                if (data.match === true || data.isMatch === true) {
                    console.log("MATCH DETECTED! Showing modal...");
                    showMatchModal(candidate);
                } else {
                    console.log("No match triggered.");
                }
            }
        } catch (e) {
            console.error("Swipe Action Error", e);
        }

        // 3. Advance Queue
        setTimeout(() => {
            currentIndex++;
            renderCard();
        }, 300);
    };

    function showMatchModal(matchedUser) {
        const modal = document.getElementById('matchModal');
        const matchName = document.getElementById('matchName');
        const myAvatar = document.querySelector('.my-avatar');
        const matchAvatar = document.querySelector('.match-avatar');

        if (!modal) return;

        // Content
        matchName.innerText = matchedUser.nombre || "¡Match!";

        const myPhoto = user.fotoUrl || "https://via.placeholder.com/150";
        const theirPhoto = matchedUser.fotoUrl || "https://via.placeholder.com/150";

        if (myAvatar) myAvatar.style.backgroundImage = `url('${myPhoto}')`;
        if (matchAvatar) matchAvatar.style.backgroundImage = `url('${theirPhoto}')`;

        // Show
        modal.classList.remove('hidden');
        modal.style.display = 'flex';

        // Event Listeners for Modal Buttons
        const closeBtn = document.getElementById('closeMatchBtn');
        const msgBtn = modal.querySelector('.message');

        if (closeBtn) {
            closeBtn.onclick = () => {
                modal.classList.add('hidden');
                modal.style.display = 'none';
            };
        }

        if (msgBtn) {
            msgBtn.onclick = () => {
                window.location.href = 'matches.html';
            };
        }
    }
}

async function loadMatches(container) {
    // Placeholder for matches
}

function initAuthModalForIndex() {
    const authModal = document.getElementById('authModal');
    const closeModalBtn = document.getElementById('closeModal');
    const loginBtnHTML = document.querySelector('.login-btn');
    const createAccountBtn = document.getElementById('createAccountBtn');

    if (!loginBtnHTML || !createAccountBtn) return;

    const modalTitle = document.getElementById('modalTitle');
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');
    const toggleText = document.getElementById('toggleText');
    let isLoginMode = true;

    function openModal(mode = 'login') {
        authModal.style.display = 'flex';
        mode === 'login' ? setLoginMode() : setRegisterMode();
    }

    function closeModal() {
        authModal.style.display = 'none';
    }

    function setLoginMode() {
        isLoginMode = true;
        modalTitle.innerText = "EMPEZAR";
        loginForm.classList.remove('hidden');
        registerForm.classList.add('hidden');
        toggleText.innerHTML = '¿No tienes cuenta? <a href="#" id="toggleAuthMode">Regístrate</a>';
        reattachToggleListener();
    }

    function setRegisterMode() {
        isLoginMode = false;
        modalTitle.innerText = "CREAR CUENTA";
        loginForm.classList.add('hidden');
        registerForm.classList.remove('hidden');
        toggleText.innerHTML = '¿Ya tienes cuenta? <a href="#" id="toggleAuthMode">Inicia Sesión</a>';
        reattachToggleListener();
    }

    function reattachToggleListener() {
        const toggleLink = document.getElementById('toggleAuthMode');
        if (toggleLink) {
            toggleLink.addEventListener('click', (e) => {
                e.preventDefault();
                isLoginMode ? setRegisterMode() : setLoginMode();
            });
        }
    }

    loginBtnHTML.addEventListener('click', (e) => { e.preventDefault(); openModal('login'); });
    createAccountBtn.addEventListener('click', (e) => { e.preventDefault(); openModal('register'); });
    closeModalBtn.addEventListener('click', closeModal);
    window.addEventListener('click', (e) => { if (e.target === authModal) closeModal(); });

    reattachToggleListener();
}

// Password Toggle Logic
window.togglePassword = function (fieldId) {
    const field = document.getElementById(fieldId);
    if (field) {
        const type = field.type === 'password' ? 'text' : 'password';
        field.type = type;
    }
};


// --- SHARED DATA FETCHING ---

async function fetchUserProfile() {
    const user = UserContext.get();
    if (!user.email && !user.id) return false;

    try {
        let response = null;

        // 1. Try Fetch by Email FIRST (Most Reliable per User Request)
        if (user.email) {
            console.log("Fetching profile by Email:", user.email);
            response = await fetch(`${USER_API}/email/${user.email}`);
        }

        // 2. Fallback: If Email failed (or didn't exist), try ID
        if ((!response || !response.ok) && user.id) {
            console.log("Fetching profile by ID:", user.id);
            response = await fetch(`${USER_API}/${user.id}`);
        }

        if (response && response.ok) {
            const userData = await response.json();

            const photo = (userData.fotoUrl && (userData.fotoUrl.startsWith('http') || userData.fotoUrl.startsWith('data:image')))
                ? userData.fotoUrl
                : 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=100&q=60';

            // Sync Context with latest data from DB
            UserContext.set({
                id: userData.id, // Always update ID to the true User ID
                nombre: userData.nombre,
                fotoUrl: photo,
                email: userData.email
            });

            // Ensure localStorage is synced with TRUE ID
            localStorage.setItem('userId', userData.id);

            // UI Updates
            updateHeaderUI(userData.nombre || "Usuario", photo);

            if (window.location.pathname.includes('profile.html')) {
                populateProfileUI(userData, photo);
            }
            return true; // Success
        } else {
            console.error("Could not load user profile via Email or ID.");
            return false;
        }
    } catch (e) {
        console.error("Error fetching user profile", e);
        return false;
    }
}

function updateHeaderUI(name, photoUrl) {
    document.querySelectorAll('.header-name').forEach(el => el.innerText = name);
    document.querySelectorAll('.header-avatar').forEach(el => {
        el.style.backgroundImage = `url('${photoUrl}')`;
    });
}

function populateProfileUI(user, photoId) {
    
    const sEmail = document.getElementById('settingsEmail');
    if (sEmail) sEmail.innerText = user.email || "No email";

    const pNameAge = document.getElementById('previewNameAge');
    if (pNameAge) pNameAge.innerText = `${user.nombre}, ${user.edad}`;

    const pBio = document.getElementById('previewBio');
    if (pBio) pBio.innerText = user.descripcion || "Sin descripción";

    const pCard = document.querySelector('.static-preview');
    if (pCard) pCard.style.backgroundImage = `url('${photoId}')`;

    // Also Populate Form Inputs if they exist
    const nameInput = document.getElementById('editName');
    if (nameInput) nameInput.value = user.nombre || "";

    const bioInput = document.getElementById('editBio');
    if (bioInput) bioInput.value = user.descripcion || "";

    // Sliders & other inputs would go here
    // dentro de populateProfileUI(user, photoId) — al final del bloque
const dInput = document.getElementById('editDistance');
const dLabel = document.getElementById('distanceLabel');
if (dInput && dLabel) {
    const value = user.distanciaMaxima || user.distancia || 10;
    dInput.value = value;
    dLabel.innerText = `${value} km`;
}

const aInput = document.getElementById('editAgeRange');
const aLabel = document.getElementById('ageRangeLabel');
if (aInput && aLabel) {
    const maxAge = user.edadMaximaBuscada || user.edadBuscadaMax || user.edad || 28;
    aInput.value = maxAge;
    aLabel.innerText = `18 - ${maxAge}`;
}

const phone = document.getElementById('editPhone');
if (phone) phone.value = user.telefono || user.phone || '';

const email = document.getElementById('editEmail');
if (email) email.value = user.email || '';

const visible = document.getElementById('editVisible');
if (visible) visible.checked = user.visible === undefined ? true : !!user.visible;

}

async function loadMatches(container) {
    const user = UserContext.get();
    if (!user.id || !container) return;

    // Load Matches from Backend
    try {
        console.log("Loading matches for User ID:", user.id);
        // Correct Endpoint: POST/GET matches for user. 
        // MatchController says: @GetMapping("/usuario/{idUsuario}")
        const res = await fetch(`${MATCH_API}/usuario/${user.id}`);

        if (res.ok) {
            const matchesDTO = await res.json();
            console.log("Matches loaded:", matchesDTO);

            // Clear existing static items first time
            if (!container.hasAttribute('data-loaded')) {
                container.innerHTML = '';
                container.setAttribute('data-loaded', 'true');
            }

            if (matchesDTO.length === 0) {
                container.innerHTML = '<div class="empty-state">No hay matches aún</div>';
                return;
            }

            container.innerHTML = ''; // Re-render fresh

            // matchesDTO contains IDs, we need to fetch User Details for each match
            // DTO: { id, idUsuario1, idUsuario2, ... }
            // We need to find "The Other Person"
            for (const m of matchesDTO) {
                const otherId = (m.idUsuario1 == user.id) ? m.idUsuario2 : m.idUsuario1;

                // Fetch details of other user
                try {
                    const uRes = await fetch(`${USER_API}/${otherId}`);
                    if (uRes.ok) {
                        const otherUser = await uRes.json();

                        const matchEl = document.createElement('div');
                        matchEl.className = 'match-bubble';
                        const photo = (otherUser.fotoUrl && otherUser.fotoUrl.startsWith('http'))
                            ? otherUser.fotoUrl
                            : "https://via.placeholder.com/80";

                        matchEl.innerHTML = `
                            <div class="match-img gold-border" style="background-image: url('${photo}')"></div>
                            <span class="match-name">${otherUser.nombre || 'User'}</span>
                        `;

                        // Click to open chat
                        matchEl.addEventListener('click', () => {
                            loadChat(otherUser);
                        });

                        container.appendChild(matchEl);
                    }
                } catch (err) {
                    console.error("Error loading match details", err);
                }
            }
        } else {
            console.warn("Matches endpoint returned error:", res.status);
        }
    } catch (e) {
        console.error("Matches load error", e);
    }
}

async function initMatchesLogic() {
    const user = UserContext.get();
    const msgListPanel = document.querySelector('.matches-list-panel'); // Reusing existing layout structure if valid
    const chatPanel = document.querySelector('.chat-conversation-panel');

    // Reuse loadMatches for the sidebar if present
    const matchesGrid = document.querySelector('.matches-grid');
    if (matchesGrid) {
        loadMatches(matchesGrid);
    }
}

async function loadChat(matchUser) {
    const chatPanel = document.querySelector('.chat-conversation-panel');
    if (!chatPanel) return;

    // Show Chat Interface
    chatPanel.innerHTML = `
        <div class="chat-header" style="width:100%; padding: 15px; border-bottom: 1px solid #eee; display:flex; align-items:center;">
            <div class="avatar" style="width:40px; height:40px; background-image:url('${matchUser.fotoUrl || ''}'); margin-right:10px;"></div>
            <h3>${matchUser.nombre}</h3>
        </div>
        <div class="chat-messages" style="flex:1; padding:20px; overflow-y:auto; width:100%;">
            <div style="text-align:center; color:#ccc;">Inicio de la conversación</div>
            <!-- Messages go here -->
        </div>
        <div class="chat-input-area" style="padding:15px; border-top:1px solid #eee; width:100%; display:flex;">
            <input type="text" placeholder="Escribe un mensaje..." style="flex:1; padding:10px; border-radius:20px; border:1px solid #ddd; margin-right:10px;">
            <button style="padding:10px 20px; border-radius:20px; border:none; background:var(--tinder-gradient); color:white; cursor:pointer;">ENVIAR</button>
        </div>
    `;

    // Fetch Messages (Optional - if endpoint exists)
    // const res = await fetch(`${MATCH_API}/messages/${matchUser.id}`); 
    // And render...
}

async function initProfileLogic() {
    const user = UserContext.get();
    if (!user.id) return;

    // 1. Fetch Latest Data
    let currentUserData = {};
    try {
        const res = await fetch(`${USER_API}/${user.id}`);
        if (res.ok) {
            currentUserData = await res.json();
            console.log("Profile Data Loaded", currentUserData);
        }
    } catch (e) {
        console.error("Profile load err", e);
    }

    // 2. Elements
    const nameInput = document.getElementById('editName');
    const bioInput = document.getElementById('editBio');
    const genderInput = document.getElementById('editGender');
    const ageRangeInput = document.getElementById('editAgeRange');
    const distanceInput = document.getElementById('editDistance');
    const saveBtn = document.getElementById('saveProfileBtn');

    // Sliders Labels
    const ageRangeLabel = document.getElementById('ageRangeLabel');
    const distanceLabel = document.getElementById('distanceLabel');

    // Photo
    const profilePhoto = document.getElementById('profilePhotoPreview');
    const photoInput = document.getElementById('profilePhotoInput');
    const uploadBtn = document.getElementById('uploadPhotoBtn');

    // 3. Populate Fields
    if (nameInput) nameInput.value = currentUserData.nombre || "";
    if (bioInput) bioInput.value = currentUserData.descripcion || "";
    if (genderInput) genderInput.value = currentUserData.genero || "Hombre";

    // Mock settings (Backend might not have these fields yet, storing in local state or extending Entity)
    // For now, if entity doesn't have minAge/maxAge/distance, we mock or use "edad" field as placeholder
    // Assuming UI has these inputs. If not, we skip.

    // 4. Input Listeners for Dynamic Labels
    if (distanceInput && distanceLabel) {
        distanceInput.addEventListener('input', (e) => {
            distanceLabel.innerText = `${e.target.value} km`;
        });
    }

    // 5. Photo Upload
    if (uploadBtn && photoInput) {
        uploadBtn.addEventListener('click', () => photoInput.click());
        photoInput.addEventListener('change', async (e) => {
            const file = e.target.files[0];
            if (!file) return;

            // Show Preview
            const reader = new FileReader();
            reader.onload = (ev) => {
                if (profilePhoto) profilePhoto.style.backgroundImage = `url('${ev.target.result}')`;
            };
            reader.readAsDataURL(file);

            // Upload Logic
            const formData = new FormData();
            formData.append('file', file);
            formData.append('userId', user.id);

            try {
                // Assuming Media Service Endpoint
                const mediaRes = await fetch(`http://localhost:8086/media/upload`, { // Adjust port if needed. User said media-service.
                    // User Request: "POST /media/upload"
                    // Need to verify Media Service Port. 
                    // script.js defined:
                    // const AUTH_API = 'http://localhost:8081/auth';
                    // const USER_API = 'http://localhost:8082/usuario';
                    // const SEG_API = 'http://localhost:8083/seguidor';
                    // const SWIPE_API = 'http://localhost:8084/swipe';
                    // const MATCH_API = 'http://localhost:8085/match';
                    // Media Service port is likely 8086 or similar? 
                    // I will check pom.xml or application.properties if I could, but User Request says "Conexión con Media-Service".
                    // I'll assume 8086 for now or define a constant.
                    method: 'POST',
                    body: formData
                });

                if (mediaRes.ok) {
                    const newUrl = await mediaRes.text(); // Or JSON
                    currentUserData.fotoUrl = newUrl;
                    UserContext.set({ fotoUrl: newUrl });
                    alert("Foto subida correctamente");
                } else {
                    alert("Error subiendo foto");
                }
            } catch (err) {
                console.error("Upload error", err);
            }
        });
    }

    // 6. Save Profile
    if (saveBtn) {
        saveBtn.addEventListener('click', async () => {
            saveBtn.innerText = "Guardando...";
            saveBtn.disabled = true;

            const updatedProfile = {
                ...currentUserData,
                nombre: nameInput ? nameInput.value : currentUserData.nombre,
                descripcion: bioInput ? bioInput.value : currentUserData.descripcion,
                genero: genderInput ? genderInput.value : currentUserData.genero
                // Add age range / distance if Entity supports it
            };

            try {
                const putRes = await fetch(`${USER_API}/${user.id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updatedProfile)
                });

                if (putRes.ok) {
                    const finalUser = await putRes.json();
                    UserContext.set({
                        nombre: finalUser.nombre,
                        descripcion: finalUser.descripcion
                        // etc 
                    });
                    alert("Perfil actualizado correctamente");
                } else {
                    alert("Error al actualizar perfil");
                }
            } catch (err) {
                console.error(err);
                alert("Error de conexión");
            } finally {
                saveBtn.innerText = "GUARDAR CAMBIOS";
                saveBtn.disabled = false;
            }
        });
    }

    // Logout
    const logoutBtn = document.querySelector('.logout-btn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            UserContext.logout();
        });
    }

    // ----------------------
// Editor de perfil (activar/guardar) + mostrar valores numéricos en sliders
// ----------------------
(function wireProfileEditor() {
    // Espera que el DOM y el initProfileLogic hayan cargado elementos
    document.addEventListener('DOMContentLoaded', () => {
        const toggleBtn = document.getElementById('toggleEditBtn');
        const saveBtn = document.getElementById('saveProfileBtn');

        const nameInput = document.getElementById('editName');
        const bioInput = document.getElementById('editBio');
        const phoneInput = document.getElementById('editPhone');
        const emailInput = document.getElementById('editEmail');
        const distanceInput = document.getElementById('editDistance');
        const ageInput = document.getElementById('editAgeRange');
        const visibleInput = document.getElementById('editVisible');

        const distanceLabel = document.getElementById('distanceLabel');
        const ageRangeLabel = document.getElementById('ageRangeLabel');

        // Helper: habilita/deshabilita campos
        function setEditing(enabled) {
            [nameInput, bioInput, phoneInput, emailInput, distanceInput, ageInput, visibleInput].forEach(el => {
                if (!el) return;
                el.disabled = !enabled;
            });
            saveBtn.classList.toggle('hidden', !enabled);
            toggleBtn.innerText = enabled ? 'Cancelar' : 'Modificar';
            toggleBtn.dataset.editing = enabled ? 'true' : 'false';
        }

        // Inicial: no editar
        setEditing(false);

        // Mostrar valores al mover sliders
        if (distanceInput && distanceLabel) {
            distanceLabel.innerText = `${distanceInput.value} km`;
            distanceInput.addEventListener('input', (e) => {
                distanceLabel.innerText = `${e.target.value} km`;
            });
        }

        if (ageInput && ageRangeLabel) {
            ageRangeLabel.innerText = `18 - ${ageInput.value}`;
            ageInput.addEventListener('input', (e) => {
                ageRangeLabel.innerText = `18 - ${e.target.value}`;
            });
        }

        // Toggle editar/cancelar
        if (toggleBtn) {
            toggleBtn.addEventListener('click', () => {
                const editing = toggleBtn.dataset.editing === 'true';
                if (!editing) {
                    setEditing(true);
                } else {
                    // cancelar: recargar datos desde contexto/servidor para restaurar
                    // Llamamos a fetchUserProfile para re-popular UI (ya existente)
                    fetchUserProfile(); // actualiza UI con los datos del servidor
                    setEditing(false);
                }
            });
        }

        // Guardar cambios: hace PUT al endpoint /usuario/{id}
        if (saveBtn) {
            saveBtn.addEventListener('click', async () => {
                saveBtn.innerText = 'Guardando...';
                saveBtn.disabled = true;

                // Datos a actualizar
                const user = UserContext.get();
                if (!user || !user.id) {
                    alert('Usuario no identificado. Inicia sesión de nuevo.');
                    saveBtn.innerText = 'Guardar cambios';
                    saveBtn.disabled = false;
                    return;
                }

                const payload = {
                    nombre: nameInput ? nameInput.value : undefined,
                    descripcion: bioInput ? bioInput.value : undefined,
                    telefono: phoneInput ? phoneInput.value : undefined,
                    email: emailInput ? emailInput.value : undefined,
                    // campos de discovery (si tu backend los soporta)
                    distanciaMaxima: distanceInput ? parseInt(distanceInput.value) : undefined,
                    edadMaximaBuscada: ageInput ? parseInt(ageInput.value) : undefined,
                    visible: visibleInput ? !!visibleInput.checked : undefined
                };

                // Limpia undefined
                Object.keys(payload).forEach(k => { if (payload[k] === undefined) delete payload[k]; });

                try {
                    const res = await fetch(`${USER_API}/${user.id}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(payload)
                    });

                    if (res.ok) {
                        const updated = await res.json();
                        // Actualiza contexto y UI
                        UserContext.set({
                            nombre: updated.nombre || updated.name || user.nombre,
                            descripcion: updated.descripcion || user.descripcion,
                            fotoUrl: updated.fotoUrl || user.fotoUrl
                        });
                        alert('Perfil actualizado correctamente.');
                        // refresca UI y sale de modo edición
                        await fetchUserProfile();
                        setEditing(false);
                    } else {
                        const txt = await res.text();
                        console.error('PUT usuario error:', res.status, txt);
                        alert('No se pudo actualizar el perfil: ' + (txt || res.status));
                    }
                } catch (err) {
                    console.error('Error guardando perfil', err);
                    alert('Error de conexión al guardar perfil.');
                } finally {
                    saveBtn.innerText = 'Guardar cambios';
                    saveBtn.disabled = false;
                }
            });
        }
    });
})();

}
