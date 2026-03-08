import { useEffect, useMemo, useState } from "react";
import { Navigate, Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { API_BASE, authApi } from "./api";
import HealthCareProfessionalPage from "./pages/HealthCareProfessionalPage";
import HomePage from "./pages/HomePage";
import MedicalSalesRepPage from "./pages/MedicalSalesRepPage";

const views = [
  { path: "/", label: "Home" },
  { path: "/healthcare-professional", label: "Health Care Professional" },
  { path: "/medical-sales-representative", label: "Medical Sales Representative" }
];

function App() {
  const navigate = useNavigate();
  const location = useLocation();
  const [token, setToken] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("Sign in to access Health Care Professional and Medical Sales Representative APIs.");

  const isAuthed = useMemo(() => token.trim().length > 0, [token]);

  useEffect(() => {
    const titleByPath = {
      "/": "Home | Healthcare Mobile Console",
      "/healthcare-professional": "Health Care Professional | Healthcare Mobile Console",
      "/medical-sales-representative": "Medical Sales Representative | Healthcare Mobile Console"
    };
    document.title = titleByPath[location.pathname] || "Healthcare Mobile Console";
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
          <p className="text-xs uppercase tracking-[0.22em] text-teal-700">vscode-remot-java-testing-ui</p>
          <h1 className="font-display text-3xl leading-tight text-teal-950">Healthcare Mobile Console</h1>
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
          <Route path="/" element={<HomePage onNavigate={navigate} />} />
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

      <nav className="menu-shell">
        <div className="menu-grid">
          {views.map((view) => (
            <button
              key={view.path}
              className={location.pathname === view.path ? "menu-item menu-item-active" : "menu-item"}
              onClick={() => navigate(view.path)}
            >
              {view.label}
            </button>
          ))}
        </div>
      </nav>
    </div>
  );
}

export default App;
