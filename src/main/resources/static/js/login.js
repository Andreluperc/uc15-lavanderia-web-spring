(function(){
  function onlyDigits(s){ return (s || "").replace(/\D/g, ""); }
  function isCpfValidBasic(cpf){
    const n = onlyDigits(cpf);
    if(n.length !== 11) return false;
    return !/^([0-9])\1{10}$/.test(n);
  }

  async function apiLogin(cpf, matricula){
    const res = await fetch("/api/operadores/login", {
      method: "POST",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify({ cpf, matricula })
    });
    return res.json();
  }

  document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("loginForm");
    const cpf = document.getElementById("cpf");
    const matricula = document.getElementById("matricula");
    const err = document.getElementById("loginError");
    if(!form) return;

    form.addEventListener("submit", async (e) => {
      e.preventDefault();
      err.textContent = "";

      const cpfVal = cpf.value.trim();
      const matVal = matricula.value.trim();

      if(!isCpfValidBasic(cpfVal)){
        err.textContent = "CPF inválido. Informe 11 dígitos (não pode ser todos iguais).";
        cpf.focus(); return;
      }
      if(matVal.length < 3){
        err.textContent = "Matrícula inválida. Informe ao menos 3 caracteres.";
        matricula.focus(); return;
      }

      try{
        const out = await apiLogin(cpfVal, matVal);
        if(out.ok){
          sessionStorage.setItem("uc15_user", JSON.stringify({ cpf: onlyDigits(cpfVal), matricula: matVal }));
          window.location.href = "dashboard.html";
        } else {
          err.textContent = out.message || "CPF ou matrícula inválidos. Verifique os dados informados.";
        }
      }catch(ex){
        err.textContent = "Erro ao conectar no servidor. Verifique se a API está rodando.";
      }
    });
  });
})();
