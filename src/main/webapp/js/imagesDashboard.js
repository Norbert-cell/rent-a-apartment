const liElements = document.querySelectorAll('div .gallery li');
const body = document.querySelector('body');

window.addEventListener('DOMContentLoaded', evt => {
    evt.preventDefault()

    liElements.forEach(function (li) {
        li.addEventListener('click', function () {
                const divElement = document.createElement('div');
                divElement.classList.add('fullScreen');
                divElement.style.visibility = 'visible';
                body.appendChild(divElement);

                const liSrc = li.querySelector('img');
                const imageElement = document.createElement('img');
                imageElement.setAttribute('src', liSrc.src);
                divElement.appendChild(imageElement);

                const buttonElement = document.createElement('button');
                buttonElement.classList.add('close');
                buttonElement.innerText = 'Close';
                divElement.appendChild(buttonElement);

                buttonElement.addEventListener('click', function () {
                    body.removeChild(divElement);
                })
            })
        })
})