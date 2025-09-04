/**
 * Context mis-use (creates context inside component).
 */
import React, { createContext, useContext } from "react";

export default function BadContextAbuse(){
  const Ctx = createContext("default"); // created per render
  const v = useContext(Ctx); // always default
  return <div>Value: {v}</div>;
}
