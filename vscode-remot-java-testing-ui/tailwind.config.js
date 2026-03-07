/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        display: ["Fraunces", "serif"],
        sans: ["Space Grotesk", "sans-serif"]
      },
      boxShadow: {
        soft: "0 12px 30px rgba(11, 67, 93, 0.12)"
      }
    }
  },
  plugins: []
};
