document.getElementById('deleteModal').addEventListener('show.bs.modal',
                                                                function(event) {
    const button = event.relatedTarget;
    const categoryId = button.getAttribute('data-id');
    const form = document.getElementById('deleteForm');
    form.setAttribute('action', `/category/delete/${categoryId}`);
});


let scrollTopButton = document.getElementById("footerbutton");

window.onscroll = scrollFunction;

function scrollFunction() {
    if (document.documentElement.scrollTop > 40) {
        scrollTopButton.style.display = "block";
    } else {
        scrollTopButton.style.display = "none";
    }
}

window.topFunction = function () {
    document.documentElement.scrollTop = 0;
    document.body.scrollTop = 0;
};