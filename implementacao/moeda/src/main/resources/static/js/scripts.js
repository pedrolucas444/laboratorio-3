/**
 * Exibe mensagens de erro na página
 * @param {string} message - Mensagem de erro
 * @param {string} elementId - ID do elemento onde mostrar o erro (padrão: 'error')
 */
function showError(message, elementId = "error") {
  const errorElement = document.getElementById(elementId);
  if (errorElement) {
    errorElement.textContent = message;
    errorElement.style.display = "block";
  }
  console.error(message);
}

/**
 * Remove mensagens de erro
 * @param {string} elementId - ID do elemento de erro (padrão: 'error')
 */
function clearError(elementId = "error") {
  const errorElement = document.getElementById(elementId);
  if (errorElement) {
    errorElement.textContent = "";
    errorElement.style.display = "none";
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
  localStorage.removeItem("usuario");
  localStorage.removeItem("tipo");
  window.location.href = "/login.html";
}

if (typeof module !== "undefined" && module.exports) {
  module.exports = { showError, clearError, isValidEmail, isValidCPF, logout };
}

/**
 * Funções utilitárias compartilhadas
 */

// Exibe mensagens de erro
function showError(message, elementId = "error") {
  const errorElement = document.getElementById(elementId);
  if (errorElement) {
    errorElement.textContent = message;
    errorElement.style.display = "block";
  }
  console.error(message);
}

// Remove mensagens de erro
function clearError(elementId = "error") {
  const errorElement = document.getElementById(elementId);
  if (errorElement) {
    errorElement.textContent = "";
    errorElement.style.display = "none";
  }
}

// Validação de campos
function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidCPF(cpf) {
  cpf = cpf.replace(/\D/g, "");
  return cpf.length === 11;
}

function isValidRG(rg) {
  rg = rg.replace(/\D/g, "");
  return rg.length >= 8 && rg.length <= 10;
}

function isValidCNPJ(cnpj) {
  cnpj = cnpj.replace(/\D/g, "");
  return cnpj.length === 14;
}

// Máscaras de campos
function applyCPFMask(cpf) {
  let value = cpf.replace(/\D/g, "");
  if (value.length > 11) value = value.substring(0, 11);

  if (value.length > 3 && value.length <= 6) {
    value = value.replace(/(\d{3})(\d)/, "$1.$2");
  } else if (value.length > 6 && value.length <= 9) {
    value = value.replace(/(\d{3})(\d{3})(\d)/, "$1.$2.$3");
  } else if (value.length > 9) {
    value = value.replace(/(\d{3})(\d{3})(\d{3})(\d)/, "$1.$2.$3-$4");
  }
  return value;
}

function applyCNPJMask(cnpj) {
  let value = cnpj.replace(/\D/g, "");
  if (value.length > 14) value = value.substring(0, 14);

  if (value.length > 2 && value.length <= 5) {
    value = value.replace(/(\d{2})(\d)/, "$1.$2");
  } else if (value.length > 5 && value.length <= 8) {
    value = value.replace(/(\d{2})(\d{3})(\d)/, "$1.$2.$3");
  } else if (value.length > 8 && value.length <= 12) {
    value = value.replace(/(\d{2})(\d{3})(\d{3})(\d)/, "$1.$2.$3/$4");
  } else if (value.length > 12) {
    value = value.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d)/, "$1.$2.$3/$4-$5");
  }
  return value;
}

// Validação de campos com exibição de erro
function validateField(element, validationFn, errorMessage) {
  if (!validationFn(element.value)) {
    showErrorForField(element, errorMessage);
    return false;
  }
  clearErrorForField(element);
  return true;
}

function showErrorForField(field, message) {
  field.classList.add("campo-invalido");
  let errorSpan = field.parentElement.querySelector(".error-message");

  if (!errorSpan) {
    errorSpan = document.createElement("span");
    errorSpan.className = "error-message";
    field.parentElement.appendChild(errorSpan);
  }

  errorSpan.textContent = message;
}

function clearErrorForField(field) {
  field.classList.remove("campo-invalido");
  const errorSpan = field.parentElement.querySelector(".error-message");
  if (errorSpan) {
    errorSpan.remove();
  }
}

// Logout
function logout() {
  localStorage.removeItem("usuario");
  localStorage.removeItem("tipo");
  window.location.href = "/login.html";
}

// Verificação de campos únicos no servidor
async function checkUniqueField(endpoint, fieldName, fieldValue, errorElement) {
  try {
    const response = await fetch(
      `${endpoint}?${fieldName}=${encodeURIComponent(fieldValue)}`
    );
    const exists = await response.json();
    if (exists) {
      showErrorForField(errorElement, `${fieldName} já cadastrado`);
      return true;
    }
    return false;
  } catch (error) {
    console.error(`Erro ao verificar ${fieldName}:`, error);
    return false;
  }
}

// Inicialização de máscaras
function initializeMasks() {
  document.querySelectorAll('[data-mask="cpf"]').forEach((input) => {
    input.addEventListener("input", function (e) {
      e.target.value = applyCPFMask(e.target.value);
    });
  });

  document.querySelectorAll('[data-mask="cnpj"]').forEach((input) => {
    input.addEventListener("input", function (e) {
      e.target.value = applyCNPJMask(e.target.value);
    });
  });
}

// Verificação de autenticação
function checkAuth(requiredType) {
  const usuario = JSON.parse(localStorage.getItem("usuario"));
  const tipo = localStorage.getItem("tipo");

  if (!usuario || tipo !== requiredType) {
    window.location.href = "login.html";
    return null;
  }
  return usuario;
}
