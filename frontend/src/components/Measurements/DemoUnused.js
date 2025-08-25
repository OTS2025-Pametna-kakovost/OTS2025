import React, { useState } from "react";

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
