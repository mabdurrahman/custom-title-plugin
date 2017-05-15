# IntelliJ custom-title-plugin

Set your own template for IntelliJ's based IDE title bar. Uses [underscore.js templates](http://underscorejs.org/#template).

You have to specify both "Project Title Template" and "File Title Template", it falls back to default templates if any field is missed or corrupted.

The following variables are available for "Project Title Template":

- `projectDefaultTitle`
- `projectName`
- `projectPath`

The following variables are available for "File Title Template":

- `projectDefaultTitle`
- `projectName`
- `projectPath`
- `fileDefaultTitle`
- `fileName`
- `filePath`
- `fileExt`

More variables going to be added on upcoming releases.

## Examples

### Default

The default templates matches the regular IntelliJ titlebar:

Project Title Template:
```
<% if (projectDefaultTitle) { %><%= projectDefaultTitle %><% } %>
```
File Title Template:
```
<% if (fileDefaultTitle) { %><%= fileDefaultTitle %><% } %>
```

### Project only title

Project Title Template:
```
<% if (projectDefaultTitle) { %><%= projectDefaultTitle %><% } %>
```
File Title Template (empty):
```
<% %>
```

### Advanced usage

Project Title Template (add week number):
```
<% Date.prototype.getWeek = function() {var onejan = new Date(this.getFullYear(),0,1); var today = new Date(this.getFullYear(),this.getMonth(),this.getDate()); var dayOfYear = ((today - onejan +1)/86400000); return Math.ceil(dayOfYear/7)}; if (projectDefaultTitle) { %><%= projectDefaultTitle %><% } %> W: <%= new Date().getWeek() %>
```
File Title Template (random):
```
<%= _.sample(["Class01.java", "Class02.java", "Class03.java", "Class04.java", "Class05.java", "Class06.java", "Class07.java", "Class08.java", "Class09.java", "Class10.java"]) %>
```

## Changes log

* Version 0.0.2 - Propagate Settings without restarts
* Version 0.0.1 - First release

## TODOs

* Add git variables (i.e. gitHead, gitAdded, gitDeleted, etc.)

## Contributing

Got a question or an idea? I'd love your input! Check out [contributing guidelines](/CONTRIBUTE.md) for ways to offer feedback and contribute.

## Credits

* [Atom custom-title package](https://github.com/postcasio/custom-title) (*Original Idea*)
* [IntelliJ simple-titles plugin](https://github.com/kjaniszewski/simple-titles-project-only) (*Base Project Structure*)
* [IntelliJ backgroundImagePlus](https://github.com/lachlankrautz/backgroundImagePlus) (*How To Add Plugin Settings*)

## License    
    
    Copyright (c) Mahmoud Abdurrahman 2017. All Rights Reserved.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
      http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.