function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].employeeId === id) {
            return i;
        }
    }
    return -1;
}

var employeeApi = Vue.resource('/simplewebapp{/id}');

Vue.component('employee-form', {
    props: ['employees','employeeAttr'],
    data: function() {
        return {
            employeeId: '',
            firstName: '',
            lastName: '',
            departmentId: '',
            jobTitle: '',
            gender: '',
            dateOfBirth: ''
        }
    },
    watch: {
        employeeAttr: function(newValue, oldValue) {
            this.employeeId = newValue.employeeId;
            this.firstName = newValue.firstName;
            this.lastName = newValue.lastName;
            this.departmentId = newValue.departmentId;
            this.jobTitle = newValue.jobTitle;
            this.gender = newValue.gender;
            this.dateOfBirth = newValue.dateOfBirth;
        }
    },
    template:
    '<div>' +
        '<input type="text" placeholder="Write firstName" v-model="firstName" />' +
        '<input type="text" placeholder="Write lastName" v-model="lastName" />' +
        '<input type="text" placeholder="Write departmentId" v-model="departmentId" />' +
        '<input type="text" placeholder="Write jobTitle" v-model="jobTitle" />' +
        '<input type="text" placeholder="Write gender" v-model="gender" style="text-transform:uppercase"/>' +
        '<input type="text" placeholder="Write dateOfBirth" v-model="dateOfBirth" />' +
        '<input type="button" value="Save" @click="save" />' +
    '</div>',
    methods: {
        save: function() {
            var employee = { employeeId: this.employeeId, firstName: this.firstName , lastName: this.lastName, departmentId: this.departmentId, jobTitle: this.jobTitle, gender: this.gender, dateOfBirth: this.dateOfBirth};
            if (this.employeeId){
                employeeApi.update({id: this.employeeId}, employee).then(result =>
                result.json().then(data => {
                var index = getIndex(this.employees, data.employeeId);
                this.employees.splice(index, 1, data);
                this.firstName = ''
                this.lastName = ''
                this.departmentId = ''
                this.jobTitle = ''
                this.gender = ''
                this.employeeId = ''
                this.dateOfBirth = ''
                })
              )
            } else {
                employeeApi.save({}, employee).then(result =>
                result.json().then(data => {
                this.employees.push(data);
                this.firstName = ''
                this.lastName = ''
                this.departmentId = ''
                this.jobTitle = ''
                this.gender = ''
                this.employeeId = ''
                this.dateOfBirth = ''
                })
              )
            }
        }
    }
});

Vue.component('employee-row', {
    props: ['employee', 'editMethod','employees'],
    template: '<div><i style="width: 25px;display: inline-block">({{ employee.employeeId }})</i> firstName: <i style="width: 70px;display: inline-block">{{ employee.firstName }}</i> ' +
        ' lastName: <i style="width: 100px;display: inline-block">{{ employee.lastName }}</i>' +
        ' departmentId: <i style="width: 75px;display: inline-block">{{ employee.departmentId }}</i>' +
        ' jobTitle: <i style="width: 110px;display: inline-block">{{ employee.jobTitle }}</i>' +
        ' gender: <i style="width: 115px;display: inline-block">{{ employee.gender }}</i>' +
        ' dateOfBirth: <i style="width: 80px;display: inline-block">{{ employee.dateOfBirth }}</i>' +
        '<span style="position: absolute;right: 100px">' +
        '<input type="button" value="Edit" @click="edit"/>' +
        '<input type="button" value="X" @click="del"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.employee);
        },
        del: function () {
            employeeApi.remove({id: this.employee.employeeId}).then(result => {
                if (result.ok) {
                    this.employees.splice(this.employees.indexOf(this.employee), 1)
                 }
            })
        }
    }
});

Vue.component('employees-list', {
    props: ['employees'],
    data: function() {
        return {
            employee: null
        }
    },
    template: '<div style="position: relative; width: 1200px;">' +
                '<employee-form :employees="employees" :employeeAttr="employee" />' +
                '<employee-row v-for="employee in employees" :key="employee.id" :employee="employee" ' +
                ':editMethod="editMethod" :employees="employees"/>' +
              '</div>',
    created: function() {
        employeeApi.get().then(result =>
            result.json().then(data =>
                data.forEach(employee => this.employees.push(employee))
            )
        )
    },
    methods: {
        editMethod: function (employee) {
            this.employee = employee;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<employees-list :employees="employees"/>',
    data: {
        employees: []
    }
})