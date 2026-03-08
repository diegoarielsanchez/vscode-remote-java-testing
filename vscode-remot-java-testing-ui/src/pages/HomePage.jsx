import { useEffect, useRef, useState } from "react";

function HomePage({ onNavigate }) {
  const [menuOpen, setMenuOpen] = useState(false);
  const menuRef = useRef(null);

  const navigateTo = (path) => {
    onNavigate(path);
    setMenuOpen(false);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (menuRef.current && !menuRef.current.contains(event.target)) {
        setMenuOpen(false);
      }
    };

    if (menuOpen) {
      document.addEventListener("mousedown", handleClickOutside);
      return () => document.removeEventListener("mousedown", handleClickOutside);
    }
  }, [menuOpen]);

  return (
    <section className="card rounded-3xl p-5 animate-enter">
      <div className="flex items-start justify-between gap-3">
        <div>
          <p className="text-xs uppercase tracking-[0.22em] text-teal-700">Home</p>
          <h2 className="mt-1 font-display text-2xl text-teal-950">Healthcare Operations Console</h2>
        </div>
        <div className="relative" ref={menuRef}>
          <button
            type="button"
            className="hamburger-btn"
            aria-label="Toggle menu"
            aria-expanded={menuOpen}
            onClick={() => setMenuOpen((prev) => !prev)}
          >
            <span className="hamburger-line" />
            <span className="hamburger-line" />
            <span className="hamburger-line" />
          </button>

          {menuOpen ? (
            <div className="hamburger-panel">
              <button className="hamburger-link" onClick={() => navigateTo("/")}>Home</button>
              <button className="hamburger-link" onClick={() => navigateTo("/healthcare-professional")}>Health Care Professional</button>
              <button className="hamburger-link" onClick={() => navigateTo("/medical-sales-representative")}>Medical Sales Representative</button>
            </div>
          ) : null}
        </div>
      </div>

      <p className="mt-2 text-sm text-slate-600">
        Choose a mobile-first workflow from the menu or tap one of the quick actions below.
      </p>

      <div className="mt-5 grid grid-cols-1 gap-3">
        <button className="quick-action" onClick={() => navigateTo("/healthcare-professional")}>Health Care Professional</button>
        <button className="quick-action" onClick={() => navigateTo("/medical-sales-representative")}>Medical Sales Representative</button>
      </div>

      <div className="mt-5 rounded-2xl bg-white/75 p-4 text-sm text-slate-600">
        <p className="font-semibold text-slate-800">Menu options</p>
        <p className="mt-1">1. Home</p>
        <p>2. Health Care Professional</p>
        <p>3. Medical Sales Representative</p>
      </div>
    </section>
  );
}

export default HomePage;
