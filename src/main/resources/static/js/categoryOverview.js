document.getElementById('deleteModal').addEventListener('show.bs.modal', function(event) {
    const button = event.relatedTarget;
    const categoryId = button.getAttribute('data-id');
    const form = document.getElementById('deleteForm');
    form.setAttribute('action', `/category/delete/${categoryId}`);
});