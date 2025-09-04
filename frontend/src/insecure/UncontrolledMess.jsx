/**
 * Mixed controlled/uncontrolled inputs.
 */
import React, { useRef, useState } from "react";

export default function UncontrolledMess(){
  const [val, setVal] = useState();
  const ref = useRef();
  return (
    <div>
      <input defaultValue="start" ref={ref} onChange={e=>setVal(e.target.value)} />
      <input value={val} onChange={e=>setVal(e.target.value)} />
      <div>Value: {val}</div>
    </div>
  );
}
