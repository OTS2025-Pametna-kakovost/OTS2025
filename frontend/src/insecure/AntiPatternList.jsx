/**
 * Missing keys in list items; inline handlers allocated per render.
 */
import React from "react";

export default function AntiPatternList(){
  const arr = Array.from({length:20}, (_,i)=>({i, v: Math.random()}));
  return (
    <ol>
      {arr.map(x => <li onClick={()=>console.log(x)}>{x.v}</li>)}
    </ol>
  );
}
