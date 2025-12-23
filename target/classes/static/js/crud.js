(function(){
  function storeKey(){ return document.body.getAttribute("data-store-key") || ""; }
  function endpoint(){
    const key = storeKey();
    if(key === "uc15_clientes") return "/api/clientes";
    if(key === "uc15_operadores") return "/api/operadores";
    return null;
  }
  function isOperadores(){ return storeKey() === "uc15_operadores"; }

  function normalizeCpf(cpf){ return (cpf||"").replace(/\D/g,""); }

  async function apiList(){
    const res = await fetch(endpoint());
    return res.json();
  }
  async function apiCreate(payload){
    const res = await fetch(endpoint(), {method:"POST", headers:{"Content-Type":"application/json"}, body: JSON.stringify(payload)});
    if(!res.ok) throw new Error(await res.text());
    return res.json();
  }
  async function apiUpdate(id, payload){
    const res = await fetch(`${endpoint()}/${id}`, {method:"PUT", headers:{"Content-Type":"application/json"}, body: JSON.stringify(payload)});
    if(!res.ok) throw new Error(await res.text());
    return res.json();
  }
  async function apiDelete(id){
    const res = await fetch(`${endpoint()}/${id}`, {method:"DELETE"});
    if(!res.ok && res.status !== 204) throw new Error(await res.text());
  }

  function filterItems(items, q){
    const query = (q||"").toLowerCase();
    if(!query) return items;
    return items.filter(it => JSON.stringify(it).toLowerCase().includes(query));
  }

  function validate(nome, cpf, extra1){
    const n = normalizeCpf(cpf);
    if(!nome || nome.trim().length < 3) return "Nome deve ter ao menos 3 caracteres.";
    if(n.length !== 11) return "CPF deve ter 11 dígitos.";
    if(/^([0-9])\1{10}$/.test(n)) return "CPF inválido (todos os dígitos iguais).";
    if(isOperadores()){
      if(!extra1 || extra1.trim().length < 3) return "Matrícula deve ter ao menos 3 caracteres.";
    }
    return "";
  }

  async function render(){
    const q = document.getElementById("q").value;
    const tbody = document.getElementById("tbody");
    tbody.innerHTML = "";

    try{
      const items = await apiList();
      document.getElementById("count").textContent = String(items.length);

      const filtered = filterItems(items, q);
      filtered.forEach(it => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${it.nome || ""}</td>
          <td>${it.cpf || ""}</td>
          <td>${isOperadores() ? (it.matricula || "") : (it.telefone || "")}</td>
          <td>
            <button class="btn small" data-edit="${it.id}">Editar</button>
            <button class="btn small danger" data-del="${it.id}">Excluir</button>
          </td>
        `;
        tbody.appendChild(tr);
      });

      document.querySelectorAll("[data-edit]").forEach(btn => btn.addEventListener("click", () => openModal(btn.getAttribute("data-edit"))));
      document.querySelectorAll("[data-del]").forEach(btn => btn.addEventListener("click", () => remove(btn.getAttribute("data-del"))));
    }catch(ex){
      document.getElementById("count").textContent = "0";
      const tr = document.createElement("tr");
      tr.innerHTML = `<td colspan="4">Erro ao carregar dados da API: ${String(ex.message || ex)}</td>`;
      tbody.appendChild(tr);
    }
  }

  async function openModal(id){
    const modal = document.getElementById("modalBackdrop");
    const title = document.getElementById("modalTitle");
    const form = document.getElementById("itemForm");
    const err = document.getElementById("formError");
    err.textContent = "";

    form.dataset.editId = id || "";
    title.textContent = id ? "Editar" : "Novo";

    if(id){
      try{
        const res = await fetch(`${endpoint()}/${id}`);
        const item = await res.json();
        form.nome.value = item.nome || "";
        form.cpf.value = item.cpf || "";
        form.extra1.value = isOperadores() ? (item.matricula || "") : (item.telefone || "");
      }catch{
        form.nome.value = ""; form.cpf.value = ""; form.extra1.value = "";
      }
    } else {
      form.nome.value = ""; form.cpf.value = ""; form.extra1.value = "";
    }

    modal.style.display = "grid";
  }

  function closeModal(){ document.getElementById("modalBackdrop").style.display = "none"; }

  async function upsert(e){
    e.preventDefault();
    const form = e.target;
    const editId = form.dataset.editId || "";
    const nome = form.nome.value.trim();
    const cpf = form.cpf.value.trim();
    const extra1 = form.extra1.value.trim();

    const msg = validate(nome, cpf, extra1);
    const err = document.getElementById("formError");
    err.textContent = msg;
    if(msg) return;

    const payload = isOperadores()
      ? { nome, cpf: normalizeCpf(cpf), matricula: extra1, perfil: "OPERADOR" }
      : { nome, cpf: normalizeCpf(cpf), telefone: extra1 };

    try{
      if(editId) await apiUpdate(editId, payload);
      else await apiCreate(payload);
      closeModal();
      await render();
    }catch(ex){
      err.textContent = String(ex.message || ex);
    }
  }

  async function remove(id){
    if(!confirm("Deseja excluir este registro?")) return;
    try{
      await apiDelete(id);
      await render();
    }catch(ex){
      alert("Erro ao excluir: " + String(ex.message || ex));
    }
  }

  document.addEventListener("DOMContentLoaded", () => {
    const btnNew = document.getElementById("btnNew");
    const btnClose = document.getElementById("btnClose");
    const btnCancel = document.getElementById("btnCancel");
    const q = document.getElementById("q");
    const form = document.getElementById("itemForm");

    if(btnNew) btnNew.addEventListener("click", () => openModal(""));
    if(btnClose) btnClose.addEventListener("click", closeModal);
    if(btnCancel) btnCancel.addEventListener("click", closeModal);
    if(q) q.addEventListener("input", () => render());
    if(form) form.addEventListener("submit", upsert);

    render();
  });
})();
