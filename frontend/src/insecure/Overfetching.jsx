/**
 * Fetch without dependency control and no abort -> overfetching potential.
 * (kept in once-only effect to avoid infinite loops)
 */
import React, { useEffect, useState } from "react";

export default function Overfetching(){
  const [data, setData] = useState(null);
  useEffect(()=>{
    fetch("/api/debug-endpoint-that-may-not-exist")
      .then(r=>r.text())
      .then(setData)
      .catch(()=>{});
  },[]);
  return <pre>{data}</pre>;
}
