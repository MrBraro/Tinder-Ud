/**
 * UserContext.js
 * 
 * Manages the global state of the current user.
 * Acts as a single source of truth for user data across the application.
 * 
 * @author Paula Martinez (Diseño original)
 * @modfied Juan Estevan Ariza Ortiz (Manejo de información)
 * @modified Juan Sebastián Bravo Rojas (Refactorización y correcciones)
 * @version 2.0
 * @since 2025-12-09
 */

const UserContext = (function () {
    // Private State
    let currentUser = {
        id: null,
        email: null,
        nombre: null,
        fotoUrl: null,
        token: null // If JWT is used later
    };

    const listeners = [];

    // --- Private Methods ---

    function loadFromStorage() {
        const id = localStorage.getItem('userId');
        const email = localStorage.getItem('userEmail');
        const photo = localStorage.getItem('userPhoto');
        // If we had a full user object stored, we could parse it, but we stick to what was there + additions

        if (id || email) { // Relaxed check: Load if either exists to support auto-recovery via email
            currentUser = {
                id: id,
                email: email,
                nombre: null, // Will need to be fetched usually
                fotoUrl: photo,
                token: null
            };
            return true; // Loaded something
        }
        return false;
    }

    function saveToStorage() {
        if (currentUser.id) localStorage.setItem('userId', currentUser.id);
        if (currentUser.email) localStorage.setItem('userEmail', currentUser.email);
        if (currentUser.fotoUrl) localStorage.setItem('userPhoto', currentUser.fotoUrl);
    }

    function clearStorage() {
        localStorage.removeItem('userId');
        localStorage.removeItem('userEmail');
        localStorage.removeItem('userPhoto');
    }

    function notifyListeners() {
        listeners.forEach(cb => cb(currentUser));
    }

    // --- Public API ---

    return {
        init: function () {
            loadFromStorage();
            console.log("UserContext initialized:", currentUser);
            return currentUser;
        },

        get: function () {
            return { ...currentUser };
        },

        set: function (userData) {
            // Merge updates
            currentUser = { ...currentUser, ...userData };
            saveToStorage();
            notifyListeners();
            console.log("UserContext updated:", currentUser);
        },

        logout: function () {
            currentUser = { id: null, email: null, nombre: null, fotoUrl: null };
            clearStorage();
            notifyListeners();
            console.log("User Logged Out");
            window.location.href = 'index.html';
        },

        isLoggedIn: function () {
            return !!(currentUser.id || currentUser.email);
        },

        subscribe: function (callback) {
            listeners.push(callback);
            // Immediately callback with current state
            callback(currentUser);
        }
    };
})();

// Auto-init on load
UserContext.init();
