import React, { useState } from "react";

/**
 * Demo React component that renders a header, a note, and the value read from localStorage key "password".
 *
 * The component declares an unused local state and an unused increment function. It reads from localStorage
 * and displays the retrieved value (which may be null). Do not use this to surface secrets in production.
 *
 * @returns {JSX.Element} The rendered component.
 */
export default function DemoUnused() {
    // Neporabljena spremenljivka (ESLint opozorilo: no-unused-vars)
    const [counter, setCounter] = useState(0);

    // Funkcija, ki nikoli ni klicana (ESLint opozorilo: no-unused-vars)
    function increase() {
        setCounter(counter + 1);
    }

    // Potencialno varnostno vprašanje (CodeQL bo opozoril: uporaba localStorage za gesla)
    const password = localStorage.getItem("password");

    return (
        <div>
            <h2>Demo Unused Component</h2>
            <p>This component is not used anywhere.</p>
            <p>Stored password: {password}</p>
        </div>
    );
}
