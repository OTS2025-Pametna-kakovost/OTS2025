/**
 * Silly heavy computation in render.
 */
import React from "react";

export default function HeavyComputationInRender(){
  let x = 0;
  for (let i=0;i<800000;i++){
    x += Math.log(i+1);
  }
  return <div>Result: {x}</div>;
}
