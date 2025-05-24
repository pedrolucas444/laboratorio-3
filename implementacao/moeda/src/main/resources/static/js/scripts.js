/**
 * Exibe mensagens de erro na página
 * @param {string} message - Mensagem de erro
 * @param {string} elementId - ID do elemento onde mostrar o erro (padrão: 'error')
 */
function showError(message, elementId = 'error') {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.textContent = message;
        errorElement.style.display = 'block';
    }
    console.error(message);
}

/**
 * Remove mensagens de erro
 * @param {string} elementId - ID do elemento de erro (padrão: 'error')
 */
function clearError(elementId = 'error') {
    const errorElement = document.getElementById(elementId);
    if (errorElement) {
        errorElement.textContent = '';
        errorElement.style.display = 'none';
    }
}

/**
 * Valida email
 * @param {string} email 
 * @returns {boolean}
 */
function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

/**
 * Valida CPF (formato apenas)
 * @param {string} cpf 
 * @returns {boolean}
 */
function isValidCPF(cpf) {
    return /^\d{3}\.?\d{3}\.?\d{3}-?\d{2}$/.test(cpf);
}

function logout() {
    localStorage.removeItem('usuario');
    localStorage.removeItem('tipo');
    window.location.href = '/login.html';
}

if (typeof module !== 'undefined' && module.exports) {
    module.exports = { showError, clearError, isValidEmail, isValidCPF, logout };
}