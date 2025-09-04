/**
 * Dangerous innerHTML use.
 */
import React from "react";

export default function XSSInnerHTML({ html }){
  return <div dangerouslySetInnerHTML={{ __html: html }} />;
}
