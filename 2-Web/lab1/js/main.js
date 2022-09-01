"use strict";

class Application {
    components = {
        r: document.getElementById("r"),
        buttons: document.getElementById("r"),
        x: document.getElementById("x"),
        form: document.getElementById("form"),
        submit: document.getElementById('#form [type="submit"]')
    }

    messageBoxForm = document.querySelector("#message-box form");

    animations = new AnimationProcessor();

    activeRadioButton() {
        return document.querySelector('input[type="ratio"]:checked');
    }

    rButtonHandler(e){
        const r = parseFloat(e.target.dataset.r);
        const smallButtons = document.getElementsByClassName("small");
        Array.from(smallButtons).forEach(b =>
            b.classList.remove("small")
        );
        e.target.classList.add("small");
        this.components.x.value = x;
    }

    validateAndParse(x,y,r){
        const xMin = -3., xMax = 5.;
        const yValues = [-5.,-4.,-3.,-2.,-1.,0.,1.,2.,3.];
        const rValues = [1.,2.,3.,4.,5.];

        let px, py, pr;

        px = parseFloat(px);
        if(isNaN(x.trim()) || isNaN(px)){
            this.animations.showMessageBox("x must be a number!");
            return [null, null, null];
        }
        if(isNaN(y.trim()) || isNaN(py)){
            this.animations.showMessageBox("y must be a number!");
            return [null, null, null];
        }
        if(isNaN(r.trim()) || isNaN(pr)){
            this.animations.showMessageBox("r must be a number!");
            return [null, null, null];
        }

        if(xMin >= px || xMax <= px){
            this.animations.showMessageBox("x must be in a range (-5, 5)")
            return [null, null, null];
        }
        if(!yValues.includes(px)){
            this.animations.showMessageBox("use RatioButton for y");
            return [null, null, null];
        }
        if(!rValues.includes(pr)){
            this.animations.showMessageBox("use buttons for r");
            return [null, null, null];
        }

        return [px,py,pr];
    }

    async formSubmitHandler(e){
        e.preventDefault();
        this.components.submit.textContent = "Processing...";
        this.components.submit.setAttribute("disabled","disabled");
        const [x,y,r] = this.validateAndParse(this.components.x.value,this.activeRadioButton().value,this.components.r.value);
        if(r !== null){
            try{
                const response = await fetch("api/server.php", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({x,y,r})
                });

                const json = await response.json();
                if(response.status === 200) {
                    await this.animations.shoot(x,y,r,json.result, json.time_now, json.script_time);
                } else {
                    this.animations.showMessageBox("server error: " + json.message);
                }
            }
            catch (error){
                this.animations.showMessageBox("Can't connect to server!");
                console.log(error);
            }
        }
        this.components.submit.removeAttribute("disabled");
        this.components.textContent = "Click to Select";
    }

    dismissMessageBox(e){
        e.preventDefault();
        this.animations.dismissMessageBox();
    }

    constructor() {
        const selectedRButton = document.querySelector(`button[data-r="${this.components.r.value}"]`);
        if(selectedRButton) {
            selectedRButton.classList.add("small");
        } else {
            this.components.r.value = "";
        }

        Array.from(this.components.buttons).forEach(b =>
            b.addEventListener("click", this.rButtonHandler.bind(this))    
        );

        this.components.form.addEventListener("submit", this.formSubmitHandler.bind(this));
        this.messageBoxForm.addEventListener("submit", this.dismissMessageBox.bind(this));
    }
}

const app = new Application();