/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.{html,js,ts,jsx,tsx}', // Thymeleaf templates and any JavaScript/TypeScript files
    './src/main/resources/static/**/*.{html,js,jsx}' // Static HTML and JS files
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
