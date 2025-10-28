import pluginVue from "eslint-plugin-vue";

export default [
  ...pluginVue.configs["flat/recommended"],
  {
    rules: {
      "semi": ["error", "always"],
      "quotes": ["error", "double"],
    },
  },
];

