(function(){
  const STORAGE_KEY = "uc15_theme";
  function applyTheme(theme){
    document.documentElement.setAttribute("data-theme", theme);
    localStorage.setItem(STORAGE_KEY, theme);
  }
  function toggleTheme(){
    const current = localStorage.getItem(STORAGE_KEY) || "dark";
    applyTheme(current === "dark" ? "light" : "dark");
  }
  window.UC15 = window.UC15 || {};
  window.UC15.theme = { applyTheme, toggleTheme };

  document.addEventListener("DOMContentLoaded", () => {
    const saved = localStorage.getItem(STORAGE_KEY) || "dark";
    applyTheme(saved);
    document.querySelectorAll("[data-action='toggle-theme']").forEach(btn => {
      btn.addEventListener("click", toggleTheme);
    });
  });
})();
