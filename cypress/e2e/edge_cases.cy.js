// edge_cases.cy.js
describe('Edge Case E2E Tests', () => {
    it('should show validation error for empty form submission', () => {
        cy.visit('/form');
        cy.get('form').submit();
        cy.contains('This field is required').should('be.visible');
    });

    it('should handle invalid email format', () => {
        cy.visit('/form');
        cy.get('input[name="email"]').type('invalid-email');
        cy.get('form').submit();
        cy.contains('Invalid email address').should('be.visible');
    });

    it('should display error on server failure', () => {
        cy.intercept('POST', '/api/submit', { statusCode: 500 }).as('submitFail');
        cy.visit('/form');
        cy.get('input[name="email"]').type('test@example.com');
        cy.get('form').submit();
        cy.wait('@submitFail');
        cy.contains('Server error, please try again').should('be.visible');
    });

    it('should prevent input longer than max length', () => {
        cy.visit('/form');
        cy.get('input[name="username"]').type('a'.repeat(101));
        cy.get('input[name="username"]').should('have.value', 'a'.repeat(100));
    });

    it('should handle slow network gracefully', () => {
        cy.intercept('POST', '/api/submit', (req) => {
            req.on('response', (res) => {
                res.setDelay(5000);
            });
        }).as('slowSubmit');
        cy.visit('/form');
        cy.get('input[name="email"]').type('test@example.com');
        cy.get('form').submit();
        cy.contains('Submitting...').should('be.visible');
        cy.wait('@slowSubmit');
        cy.contains('Submission successful').should('be.visible');
    });
});