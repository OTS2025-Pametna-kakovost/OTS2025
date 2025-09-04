/**
 * Uses UNSAFE lifecycle.
 */
import React from "react";

export default class DeprecatedLifecycle extends React.Component{
  UNSAFE_componentWillMount(){
    this.state = { a: 1 };
  }
  render(){
    return <div>Deprecated lifecycle</div>;
  }
}
