const API_BASE = import.meta.env.VITE_API_BASE_URL || "http://localhost:8085";

const buildQuery = (params = {}) => {
  const search = new URLSearchParams();
  Object.entries(params).forEach(([k, v]) => {
    if (v !== undefined && v !== null && `${v}`.trim() !== "") {
      search.set(k, `${v}`);
    }
  });
  const query = search.toString();
  return query ? `?${query}` : "";
};

async function request(path, options = {}) {
  const { token, query, body, method = "GET" } = options;
  const headers = {
    "Content-Type": "application/json"
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await fetch(`${API_BASE}${path}${buildQuery(query)}`, {
    method,
    headers,
    body: body ? JSON.stringify(body) : undefined
  });

  const raw = await response.text();
  let data = raw;

  try {
    data = raw ? JSON.parse(raw) : null;
  } catch {
    data = raw;
  }

  if (!response.ok) {
    const message = typeof data === "string" ? data : data?.message || "Request failed";
    throw new Error(message);
  }

  return data;
}

export const authApi = {
  login: (payload) => request("/auth/login", { method: "POST", body: payload })
};

export const salesRepApi = {
  list: (token, filters) =>
    request("/api/v1/medicalsalesrep/list", {
      method: "POST",
      token,
      query: {
        firstName: filters.firstName,
        lastName: filters.lastName,
        page: filters.page,
        pageSize: filters.pageSize
      }
    }),
  create: (token, payload) =>
    request("/api/v1/medicalsalesrep/create", {
      method: "POST",
      token,
      body: payload
    }),
  update: (token, payload) =>
    request("/api/v1/medicalsalesrep/update", {
      method: "PUT",
      token,
      body: payload
    }),
  activate: (token, medicalSalesRepId) =>
    request("/api/v1/medicalsalesrep/activate", {
      method: "POST",
      token,
      body: { medicalSalesRepId }
    }),
  deactivate: (token, medicalSalesRepId) =>
    request("/api/v1/medicalsalesrep/deactivate", {
      method: "POST",
      token,
      body: { medicalSalesRepId }
    })
};

export { API_BASE };
