(function(){
  async function count(path){
    try{
      const res = await fetch(path);
      const arr = await res.json();
      return Array.isArray(arr) ? arr.length : 0;
    }catch{
      return 0;
    }
  }

  document.addEventListener("DOMContentLoaded", async () => {
    document.getElementById("kpiClientes").textContent = String(await count("/api/clientes"));
    document.getElementById("kpiOperadores").textContent = String(await count("/api/operadores"));

    const user = sessionStorage.getItem("uc15_user");
    if(user){
      const u = JSON.parse(user);
      document.getElementById("userBadge").textContent = u.cpf || "usuário";
    }
    document.getElementById("btnLogout").addEventListener("click", () => {
      sessionStorage.removeItem("uc15_user");
      window.location.href = "login.html";
    });
  });
})();
