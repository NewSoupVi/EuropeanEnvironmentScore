import { describe, it, expect } from "vitest";
import { mount } from "@vue/test-utils";
import { createRouter, createMemoryHistory } from "vue-router";
import HomePage from "./views/HomePage.vue";
import AnalyzePage from "./views/AnalyzePage.vue";
import i18n from "./i18n.js";

describe("Integration Tests", () => {
  it("should render HomePage at / without errors", async () => {
    const router = createRouter({
      history: createMemoryHistory(),
      routes: [{ path: "/", component: HomePage }],
    });

    router.push("/");
    await router.isReady();

    const wrapper = mount(HomePage, {
      global: {
        plugins: [router, i18n],
      },
    });

    expect(wrapper.exists()).toBe(true);
  });

  it("should render AnalyzePage at /analyze with valid lat/lng without errors", async () => {
    const router = createRouter({
      history: createMemoryHistory(),
      routes: [{ path: "/analyze", component: AnalyzePage }],
    });

    router.push("/analyze?lat=40.7128&lng=-74.0060");
    await router.isReady();

    const wrapper = mount(AnalyzePage, {
      global: {
        plugins: [router, i18n],
      },
    });

    expect(wrapper.exists()).toBe(true);
  });
});
