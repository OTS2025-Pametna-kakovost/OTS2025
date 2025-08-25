const { defineConfig } = require("cypress");

module.exports = defineConfig({
  projectId: 'vrsv3n',
  video: true,
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
