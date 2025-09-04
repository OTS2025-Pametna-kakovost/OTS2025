/**
 * Intentionally bad React component:
 * - giant component
 * - inline styles
 * - direct DOM manipulation
 * - unused states / variables
 * - missing keys in lists
 * - no prop-types or TS
 */
import React, { useEffect, useRef, useState } from "react";

export default function BadHugeComponent(){
  const [count, setCount] = useState(0);
  const [text, setText] = useState("hello");
  const [items, setItems] = useState(new Array(50).fill(0).map((_,i)=>({id:i,val:Math.random()})));
  const ref = useRef(null);

  useEffect(()=>{
    // direct DOM manipulation
    if (ref.current){
      ref.current.style.border = "5px solid red";
      ref.current.innerText = "BadHugeComponent mounted";
    }
    // missing cleanup for event
    window.addEventListener("resize", ()=>{
      setCount(c=>c+1);
    });
  },[]);

  function doHeavyStuff(){
    // needless heavy loop (renders will lag if called)
    let s = 0;
    for(let i=0;i<1000000;i++){ s += Math.sqrt(i); }
    return s;
  }

  return (
    <div ref={ref} style={{padding:10, background:"#eee"}}>
      <h3>BadHugeComponent</h3>
      <button onClick={()=>setCount(count+1)}>Inc</button>
      <div>Counter: {count}</div>
      <div>Calc: {doHeavyStuff()}</div>
      <ul>
        {items.map(it => <li>{it.val}</li>)}
      </ul>
      <input value={text} onChange={e=>setText(e.target.value)} />
    </div>
  );
}
