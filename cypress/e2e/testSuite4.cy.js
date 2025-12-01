describe('upravljanje izdelka - FAILING VERSION', () => {
    const product = 'Tartufi'

    beforeEach(() => {
        cy.visit('http://localhost:3000/products')
    })

    it('dodaj-izdelek FAIL', () => {
        const minTemp = 1
        const maxTemp = 4

        // Check that product does not exist
        cy.get('tbody tr').children('[id^="productsTableBodyName"]').contains(product).should('not.exist')

        // Add product
        cy.get('#addNewProductButton').click()
        cy.get('#addProductNameInput').type(product)
        cy.get('#addProductMaxInput').type(`${minTemp}`)
        cy.get('#addProductMinInput').type(`${maxTemp}`)
        cy.get('#addProductButton').click()

        // ❌ Intentionally wrong: should fail, because product now exists
        cy.get('tbody tr').children('[id^="productsTableBodyName"]').contains(product).should('not.exist')
    })

    it('uredi-izdelek FAIL', () => {
        const minTempN = 0
        const maxTempN = 5
        let nameCell = cy.get('tbody tr').children('[id^="productsTableBodyName"]').contains(product)
        nameCell.should('exist')
        nameCell.parent().find('[id^="productsTableBodyEdit"] button').click()
        cy.get('#editProductMinInput').type(`{backspace}{backspace}${minTempN}`)
        cy.get('#editProductMaxInput').type(`{backspace}{backspace}${maxTempN}`)
        cy.get('#editProductIdButton').click()
        cy.wait(2000)

        nameCell = cy.get('tbody tr').children('[id^="productsTableBodyName"]').contains(product)

        // ❌ Intentionally wrong: we expect incorrect values, should fail
        nameCell.parent().children('[id^="productsTableBodyMin"]').should('have.text', '999')
        nameCell.parent().children('[id^="productsTableBodyMax"]').should('have.text', '999')
    })

    it('brisi-izdelek FAIL', () => {
        const productCell = cy.get('tbody tr').children('[id^="productsTableBodyName"]').contains(product)
        productCell.should('exist')
        productCell.parent().find('[id^="productsTableBodyDelete"]').click()

        // ❌ Intentionally wrong: product should be gone, but we say it should exist
        productCell.should('exist')
    })
})