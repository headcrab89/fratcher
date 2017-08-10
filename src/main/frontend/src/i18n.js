import i18n from "i18next";
import XHR from "i18next-xhr-backend";

i18n
    .use(XHR)
    .init({
        fallbackLng: 'de',

        // Have a common namespace used around the full app
        ns: ['common'],
        defaultNS: 'common',

        debug: false,
        wait: true,

        interpolation: {
            // not needed for react.
            escapeValue: false,
            formatSeparator: ',',
        }
    });

export default i18n;