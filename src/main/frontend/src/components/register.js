import axios from "axios";
import React from "react";
import {translate} from "react-i18next";

class Register extends React.Component {
    constructor(props) {
        super();
    }

    render() {
        return (
            <div className="component">
                <h1>Register</h1>
            </div>
        );
    }
}


export default translate()(Register);