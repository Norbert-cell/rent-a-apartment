const ulElement = document.querySelector('div ul');
const liElements = ulElement.querySelectorAll('li');
const body = document.querySelector('body');

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

        const button = document.querySelector('button');
        button.addEventListener('click', function () {
            body.removeChild(divElement);
        })
    })
})