import { useEffect, useMemo, useRef, useState } from "react";
import { Navigate, Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { API_BASE, authApi } from "./api";
import HealthCareProfessionalPage from "./pages/HealthCareProfessionalPage";
import HomePage from "./pages/HomePage";
import MedicalSalesRepPage from "./pages/MedicalSalesRepPage";

function App() {
  const navigate = useNavigate();
  const location = useLocation();
  const [token, setToken] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [menuOpen, setMenuOpen] = useState(false);
  const [message, setMessage] = useState("Sign in to access Health Care Professional and Medical Sales Representative APIs.");
  const menuRef = useRef(null);

  const isAuthed = useMemo(() => token.trim().length > 0, [token]);

  useEffect(() => {
    const titleByPath = {
      "/": "Home | Healthcare Mobile Console",
      "/healthcare-professional": "Health Care Professional | Healthcare Mobile Console",
      "/medical-sales-representative": "Medical Sales Representative | Healthcare Mobile Console"
    };
    document.title = titleByPath[location.pathname] || "Healthcare Mobile Console";
  }, [location.pathname]);

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

  useEffect(() => {
    setMenuOpen(false);
  }, [location.pathname]);

  const setSuccess = (text) => setMessage(`OK: ${text}`);
  const setError = (err) => setMessage(`Error: ${err.message || String(err)}`);

  const onLogin = async (event) => {
    event.preventDefault();
    setLoading(true);
    try {
      const response = await authApi.login({ username, password });
      setToken(response.token || "");
      setSuccess(`Authenticated as ${response.username || username}`);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const onLogout = () => {
    setToken("");
    setMessage("Logged out.");
  };

  return (
    <div className="min-h-screen bg-app px-4 py-6 text-slate-900">
      <main className="mx-auto w-full max-w-xl space-y-5 pb-24 animate-enter">
        <section className="card rounded-3xl p-5">
          <div className="flex items-start justify-between gap-3">
            <div>
              <p className="text-xs uppercase tracking-[0.22em] text-teal-700">vscode-remot-java-testing-ui</p>
              <h1 className="font-display text-3xl leading-tight text-teal-950">Healthcare Mobile Console</h1>
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
                  <button
                    className={location.pathname === "/" ? "hamburger-link hamburger-link-active" : "hamburger-link"}
                    aria-current={location.pathname === "/" ? "page" : undefined}
                    onClick={() => navigate("/")}
                  >
                    Home
                  </button>
                  <button
                    className={location.pathname === "/healthcare-professional" ? "hamburger-link hamburger-link-active" : "hamburger-link"}
                    aria-current={location.pathname === "/healthcare-professional" ? "page" : undefined}
                    onClick={() => navigate("/healthcare-professional")}
                  >
                    Health Care Professional
                  </button>
                  <button
                    className={location.pathname === "/medical-sales-representative" ? "hamburger-link hamburger-link-active" : "hamburger-link"}
                    aria-current={location.pathname === "/medical-sales-representative" ? "page" : undefined}
                    onClick={() => navigate("/medical-sales-representative")}
                  >
                    Medical Sales Representative
                  </button>
                </div>
              ) : null}
            </div>
          </div>
          <p className="mt-2 text-sm text-slate-600">
            Mobile-first React + Tailwind interface with menu navigation.
          </p>
          <p className="mt-1 break-all text-xs text-slate-500">API base: {API_BASE}</p>
        </section>

        <section className="card rounded-3xl p-5">
          <h2 className="mb-3 font-display text-xl text-teal-900">1) Authenticate</h2>
          <form onSubmit={onLogin} className="space-y-3">
            <input
              className="input"
              placeholder="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <input
              className="input"
              type="password"
              placeholder="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button className="btn-primary w-full" disabled={loading}>
              {loading ? "Working..." : "Sign in"}
            </button>
          </form>
          <button className="btn-secondary mt-3 w-full" disabled={!isAuthed || loading} onClick={onLogout}>
            Logout
          </button>
          {isAuthed ? (
            <p className="mt-3 text-xs text-emerald-700">
              Authenticated. Use the menu to switch between pages.
            </p>
          ) : (
            <p className="mt-3 text-xs text-slate-500">
              All protected calls require Bearer token. Sign in first.
            </p>
          )}
        </section>

        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route
            path="/healthcare-professional"
            element={<HealthCareProfessionalPage token={token} isAuthed={isAuthed} />}
          />
          <Route
            path="/medical-sales-representative"
            element={<MedicalSalesRepPage token={token} isAuthed={isAuthed} />}
          />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>

        <p className="rounded-2xl bg-white/70 px-4 py-3 text-sm shadow-soft">{message}</p>
      </main>
    </div>
  );
}

export default App;
