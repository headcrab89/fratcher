import axios from "axios";
import React from "react";
import ReactDOM from "react-dom";
import Authentication from "./components/authentication";

import TextList from "./components/text_list";

axios.defaults.headers.common['Authorization'] = 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJibHViIiwianRpIjoiMSJ9.JQ8gDbqJ3J9YfjGmQtWIhZZQh0MZCGa83WiviiqSlRJ5Ub9-jTuSIo0JPVGaaaGwz7hwtd-VOaKGhVmg3VZpyg';

ReactDOM.render(
    <div>
        <TextList />
        <Authentication/>
    </div>,
    document.getElementById('root'));



