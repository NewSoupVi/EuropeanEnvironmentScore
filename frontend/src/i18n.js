import { createI18n } from "vue-i18n";
import en from "./locales/en.json";
import de from "./locales/de.json";

const messages = {
  en,
  de,
};

// Get saved language preference or default to 'en'
// Check if we're in browser environment before accessing localStorage
const savedLocale = (typeof window !== "undefined" && localStorage.getItem("preferred-language")) || "en";

const i18n = createI18n({
  legacy: false,
  locale: savedLocale,
  fallbackLocale: "en",
  messages,
});

export default i18n;
