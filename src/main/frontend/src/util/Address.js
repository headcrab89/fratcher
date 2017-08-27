class Address {
    constructor() {
        this.setWebsocketAddress();
    }

    setWebsocketAddress () {
        var host = window.location.host;
        this.websocket =  `ws://${host}/message`;
    }
}

export default (new Address);