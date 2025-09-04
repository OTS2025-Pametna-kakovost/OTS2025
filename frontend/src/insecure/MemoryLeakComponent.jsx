/**
 * Interval without cleanup -> memory leak
 */
import React, { useEffect, useState } from "react";

export default function MemoryLeakComponent(){
  const [tick, setTick] = useState(0);
  useEffect(()=>{
    const id = setInterval(()=>setTick(t=>t+1), 500);
    // wrong cleanup (no cleanup)
    // return () => clearInterval(id);
  },[]);
  return <div>Tick: {tick}</div>;
}
