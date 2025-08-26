import React, { useState } from "react";

/**
 * Demo React component that renders a heading and displays the "password" value read from localStorage.
 *
 * This component is intended as a minimal, unused/demo component. It reads the "password" key from
 * localStorage and displays it twice; there is no error handling and the component includes an unused
 * counter state and increment function for demonstration only. Reading passwords from localStorage is
 * insecure and should be avoided in production.
 * @returns {JSX.Element} The rendered JSX for the demo component.
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
            <p>Stored username: {password}</p>
        </div>
    );
}
