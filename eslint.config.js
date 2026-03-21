export default [
  {
    ignores: [
      "webapp/views/**/assets/script/bootstrap.bundle.js",
      "webapp/views/**/assets/script/notiflix-aio-3.2.8.min.js"
    ]
  },
  {
    files: ["webapp/views/**/assets/script/*.js"],
    languageOptions: {
      ecmaVersion: "latest",
      sourceType: "script",
      globals: {
        window: "readonly",
        document: "readonly",
        console: "readonly",
        fetch: "readonly",
        localStorage: "readonly",
        sessionStorage: "readonly",
        Chart: "readonly",
        Notiflix: "readonly",
        bootstrap: "readonly",
        URLSearchParams: "readonly",
        FormData: "readonly",
        Blob: "readonly"
      }
    },
    rules: {
      camelcase: [
        "error",
        {
          properties: "never",
          ignoreDestructuring: true,
          ignoreImports: true,
          ignoreGlobals: false,
          allow: ["^__[a-zA-Z0-9_]+__$"]
        }
      ]
    }
  }
];
