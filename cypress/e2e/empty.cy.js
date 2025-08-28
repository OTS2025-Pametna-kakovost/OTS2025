/* ==== Test Created with Cypress Studio ==== */
it('testMilka', function() {
  /* ==== Generated with Cypress Studio ==== */
  cy.visit('localhost:3000');
  cy.get('.css-1t6c9ts > [href="/products"] > .MuiButtonBase-root').click();
  cy.get('#productSearchBox').clear('m');
  cy.get('#productSearchBox').type('milka');
  cy.get('[data-testid="SearchOutlinedIcon"]').click();
  cy.get('#productSearchBox').clear('milka{enter}');
  cy.get('#productSearchBox').type('milka{enter}');
  cy.get('#productsTableBodyName1').should('have.text', 'Milka Classic');
  /* ==== End Cypress Studio ==== */
});