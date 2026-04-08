function HomePage() {
  return (
    <section className="card rounded-3xl p-5 animate-enter">
      <div>
        <p className="text-xs uppercase tracking-[0.22em] text-teal-700">Home</p>
        <h2 className="mt-1 font-display text-2xl text-teal-950">Healthcare Operations Console</h2>
      </div>

      <p className="mt-2 text-sm text-slate-600">
        Use the hamburger menu to open each page.
      </p>

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
