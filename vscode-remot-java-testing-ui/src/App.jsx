import { useMemo, useState } from "react";
import { API_BASE, authApi, salesRepApi } from "./api";

const initialCreate = { name: "", surname: "", email: "" };
const initialUpdate = { id: "", name: "", surname: "", email: "" };
const initialFilters = { firstName: "", lastName: "", page: 1, pageSize: 10 };

function App() {
  const [token, setToken] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const [filters, setFilters] = useState(initialFilters);
  const [createForm, setCreateForm] = useState(initialCreate);
  const [updateForm, setUpdateForm] = useState(initialUpdate);
  const [actionId, setActionId] = useState("");

  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("Sign in to start managing Medical Sales Reps.");

  const isAuthed = useMemo(() => token.trim().length > 0, [token]);

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

  const onList = async () => {
    setLoading(true);
    try {
      const data = await salesRepApi.list(token, filters);
      setRows(Array.isArray(data) ? data : []);
      setSuccess(`Loaded ${Array.isArray(data) ? data.length : 0} records`);
    } catch (err) {
      setError(err);
    } finally {
      setLoading(false);
    }
  };

  const onCreate = async (event) => {
    event.preventDefault();
    setLoading(true);
    try {
      await salesRepApi.create(token, createForm);
      setCreateForm(initialCreate);
      setSuccess("Medical Sales Rep created");
      await onList();
    } catch (err) {
      setError(err);
      setLoading(false);
    }
  };

  const onUpdate = async (event) => {
    event.preventDefault();
    setLoading(true);
    try {
      await salesRepApi.update(token, updateForm);
      setSuccess("Medical Sales Rep updated");
      await onList();
    } catch (err) {
      setError(err);
      setLoading(false);
    }
  };

  const onActivate = async () => {
    if (!actionId.trim()) return;
    setLoading(true);
    try {
      await salesRepApi.activate(token, actionId.trim());
      setSuccess(`Activated ID ${actionId.trim()}`);
      await onList();
    } catch (err) {
      setError(err);
      setLoading(false);
    }
  };

  const onDeactivate = async () => {
    if (!actionId.trim()) return;
    setLoading(true);
    try {
      await salesRepApi.deactivate(token, actionId.trim());
      setSuccess(`Deactivated ID ${actionId.trim()}`);
      await onList();
    } catch (err) {
      setError(err);
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-app px-4 py-6 text-slate-900">
      <main className="mx-auto w-full max-w-xl space-y-5 animate-enter">
        <section className="card rounded-3xl p-5">
          <p className="text-xs uppercase tracking-[0.22em] text-teal-700">vscode-remot-java-testing-ui</p>
          <h1 className="font-display text-3xl leading-tight text-teal-950">Medical Sales Rep Mobile Console</h1>
          <p className="mt-2 text-sm text-slate-600">
            Mobile-first React + Tailwind interface for <code>MedicalSalesRepController</code>.
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
          {isAuthed ? (
            <p className="mt-3 text-xs text-emerald-700">
              Authenticated. Bearer token is loaded and API actions are enabled.
            </p>
          ) : (
            <p className="mt-3 text-xs text-slate-500">
              All <code>/api/**</code> calls require Bearer token. Sign in first.
            </p>
          )}
        </section>

        <section className="card rounded-3xl p-5">
          <h2 className="mb-3 font-display text-xl text-teal-900">2) Search / List</h2>
          <div className="grid grid-cols-1 gap-3 sm:grid-cols-2">
            <input
              className="input"
              placeholder="firstName"
              value={filters.firstName}
              onChange={(e) => setFilters((prev) => ({ ...prev, firstName: e.target.value }))}
            />
            <input
              className="input"
              placeholder="lastName"
              value={filters.lastName}
              onChange={(e) => setFilters((prev) => ({ ...prev, lastName: e.target.value }))}
            />
          </div>
          <div className="mt-3 grid grid-cols-2 gap-3">
            <input
              className="input"
              type="number"
              min="1"
              placeholder="page"
              value={filters.page}
              onChange={(e) => setFilters((prev) => ({ ...prev, page: Number(e.target.value) || 1 }))}
            />
            <input
              className="input"
              type="number"
              min="1"
              placeholder="pageSize"
              value={filters.pageSize}
              onChange={(e) => setFilters((prev) => ({ ...prev, pageSize: Number(e.target.value) || 10 }))}
            />
          </div>
          <button className="btn-primary mt-3 w-full" onClick={onList} disabled={!isAuthed || loading}>
            Load list
          </button>
        </section>

        <section className="card rounded-3xl p-5">
          <h2 className="mb-3 font-display text-xl text-teal-900">3) Create</h2>
          <form className="space-y-3" onSubmit={onCreate}>
            <input
              className="input"
              placeholder="name"
              value={createForm.name}
              onChange={(e) => setCreateForm((prev) => ({ ...prev, name: e.target.value }))}
            />
            <input
              className="input"
              placeholder="surname"
              value={createForm.surname}
              onChange={(e) => setCreateForm((prev) => ({ ...prev, surname: e.target.value }))}
            />
            <input
              className="input"
              placeholder="email"
              type="email"
              value={createForm.email}
              onChange={(e) => setCreateForm((prev) => ({ ...prev, email: e.target.value }))}
            />
            <button className="btn-primary w-full" disabled={!isAuthed || loading}>Create</button>
          </form>
        </section>

        <section className="card rounded-3xl p-5">
          <h2 className="mb-3 font-display text-xl text-teal-900">4) Update</h2>
          <form className="space-y-3" onSubmit={onUpdate}>
            <input
              className="input"
              placeholder="id"
              value={updateForm.id}
              onChange={(e) => setUpdateForm((prev) => ({ ...prev, id: e.target.value }))}
            />
            <input
              className="input"
              placeholder="name"
              value={updateForm.name}
              onChange={(e) => setUpdateForm((prev) => ({ ...prev, name: e.target.value }))}
            />
            <input
              className="input"
              placeholder="surname"
              value={updateForm.surname}
              onChange={(e) => setUpdateForm((prev) => ({ ...prev, surname: e.target.value }))}
            />
            <input
              className="input"
              type="email"
              placeholder="email"
              value={updateForm.email}
              onChange={(e) => setUpdateForm((prev) => ({ ...prev, email: e.target.value }))}
            />
            <button className="btn-primary w-full" disabled={!isAuthed || loading}>Update</button>
          </form>
        </section>

        <section className="card rounded-3xl p-5">
          <h2 className="mb-3 font-display text-xl text-teal-900">5) Activate / Deactivate</h2>
          <input
            className="input"
            placeholder="medicalSalesRepId"
            value={actionId}
            onChange={(e) => setActionId(e.target.value)}
          />
          <div className="mt-3 grid grid-cols-2 gap-3">
            <button className="btn-primary" onClick={onActivate} disabled={!isAuthed || loading || !actionId.trim()}>
              Activate
            </button>
            <button className="btn-danger" onClick={onDeactivate} disabled={!isAuthed || loading || !actionId.trim()}>
              Deactivate
            </button>
          </div>
        </section>

        <section className="card rounded-3xl p-5">
          <h2 className="mb-3 font-display text-xl text-teal-900">Records</h2>
          {rows.length === 0 ? (
            <p className="text-sm text-slate-500">No records loaded yet.</p>
          ) : (
            <ul className="space-y-3">
              {rows.map((rep, idx) => (
                <li
                  key={rep.id || idx}
                  className="record"
                  style={{ animationDelay: `${idx * 80}ms` }}
                >
                  <div className="flex items-start justify-between gap-3">
                    <div>
                      <p className="font-semibold text-slate-800">{rep.name} {rep.surname}</p>
                      <p className="text-xs text-slate-500">{rep.email}</p>
                      <p className="mt-1 break-all text-[11px] text-slate-400">{rep.id}</p>
                    </div>
                    <span className={rep.active ? "pill-on" : "pill-off"}>
                      {rep.active ? "ACTIVE" : "INACTIVE"}
                    </span>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </section>

        <p className="rounded-2xl bg-white/70 px-4 py-3 text-sm shadow-soft">{message}</p>
      </main>
    </div>
  );
}

export default App;
