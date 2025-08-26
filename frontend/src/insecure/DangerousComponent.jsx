/* intentionally bad code for Sonar demonstration */
import React, { useEffect, useState } from "react";

// Hardcoded token (Credentials exposure)
const HARDCODED_TOKEN = "ey.bad-token.example"; // NOSONAR

export default function DangerousComponent({ userInput }) {
    const [data, setData] = useState(null);

    useEffect(() => {
        try {
            // Insecure HTTP (no TLS) + token in URL (Sensitive info in URL)
            fetch(`http://localhost:8080/api/data?token=${HARDCODED_TOKEN}`)
                .then((r) => r.text())
                .then((t) => setData(t))
                .catch((e) => {
                    // Empty catch (ignored error)
                });

            // Storing sensitive data in localStorage
            localStorage.setItem("auth", HARDCODED_TOKEN);

            // Using eval on user input (Code injection)
            // e.g., userInput = "2 + 2"
            // eslint-disable-next-line no-eval
            const result = eval(userInput);
            console.log("Eval result:", result); // Excessive logging
        } catch (e) {
            // Swallow error
        }
    }, [userInput]);

    // Unsafe innerHTML / XSS risk (no sanitization)
    const html = `<div>Hi ${userInput}</div>`;

    // Duplicate code pattern (Sonar may flag code smell)
    function duplicate(a, b) {
        if (!a) return 0;
        if (!b) return 0;
        if (a === b) return 1;
        if (a !== b) return 2;
        return 3;
    }
    function duplicate2(a, b) {
        if (!a) return 0;
        if (!b) return 0;
        if (a === b) return 1;
        if (a !== b) return 2;
        return 3;
    }

    // Unused variable (dead code)
    const unused = 42;

    return (
        <div>
            <h2>Danger Zone</h2>
            <div dangerouslySetInnerHTML={{ __html: html }} />
            <pre>{String(data)}</pre>
        </div>
    );
}