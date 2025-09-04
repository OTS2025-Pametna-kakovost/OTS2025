/**
 * Excessive inline styles and redundancy.
 */
import React from "react";

export default function InlineStylesEverywhere(){
  const style = { padding: 12, margin: 12, border: "1px solid #ccc" };
  return (
    <div style={style}>
      <div style={{padding:12, margin:12, border:"1px solid #ccc"}}>Block 1</div>
      <div style={{padding:12, margin:12, border:"1px solid #ccc"}}>Block 2</div>
      <div style={{padding:12, margin:12, border:"1px solid #ccc"}}>Block 3</div>
    </div>
  );
}
