import React from "react";
import {CookiesProvider} from "react-cookie";
import ReactDOM from "react-dom";

import Authentication from "./components/authentication";
import TextList from "./components/text_list";

ReactDOM.render(
    <CookiesProvider>
        <div>
            <TextList />
            <Authentication/>
        </div>
    </CookiesProvider>,
    document.getElementById('root'));



