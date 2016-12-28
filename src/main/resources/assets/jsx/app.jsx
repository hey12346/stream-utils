const successModeLabels = ['Success', 'Failure'];

class Dropzone extends React.Component {
  render() {
    return <form action={this.props.action} className="dropzone" id="my-awesome-dropzone"></form>;
  }
}

class Instr extends React.Component {
  render() {
    return (
    	<div>
		    <p>
		      Below is the <font color="red">Dropzone</font>.
		      To upload PDF files, please either click the dropzone or drag and drop pdfs onto the dropzone.
		    </p>
		    <p>
		      {this.props.message}
		   </p>
		</div>
    );
  }
}

class DropzoneApp extends React.Component {
  render() {
  	if(this.props.type === "pdf") {
  		return (
	    	<div>
	          <Instr message="When finished, click Download to get the final concatanated PDF."/>
	          <Dropzone action="/pdf-upload"/>
	          <Download link="/pdf" />
	        </div>
    	);
  	} else {
  		return (
	    	<div>
	          <Instr message="When finished, click Download to get the final compiled ZIP file."/>
	          <Dropzone action="/zip-upload"/>
	          <Download link="/zip" />
	        </div>
    	);
  	}
  }
}

class Download extends React.Component {
  
  render() {
    return <a href={this.props.link}>download now</a>;
  }
}

class Navigation extends React.Component {
  constructor(props) {
    super(props);
    this.state = {showHome: true};

    // This binding is necessary to make `this` work in the callback
    this.toggle = this.toggle.bind(this);
  }

  toggle() {
    this.setState(prevState => ({
      showHome: !prevState.showHome
    }));
  }

  render() {
    if(this.state.showHome) {
      $("#pdf").show();
      $("#zip").hide();
    }

    else {
      $("#pdf").hide();
      $("#zip").show();
    }

    return (
      <div className="header clearfix">
        <nav className="nav float-xs-right">
          <ul className="nav nav-pills">
            <li className="nav-item" id="homeButton">
              <a className={this.state.showHome ? 'nav-link active': 'nav-link'} href="#" onClick={this.toggle}>PDf Concatanator</a>
            </li>
            <li className="nav-item" id="docButton">
              <a className={this.state.showHome ? 'nav-link': 'nav-link active'} href="#" onClick={this.toggle}>Zip File Creator</a>
            </li>
          </ul>
        </nav>
      </div>
    );
  }
}

function createAutoClosingAlert(message) {
  const alert = document.createElement('div');
  alert.className = 'alert alert-info fade in';

  const content = document.createElement('div');
  content.append(message);
  alert.append(content);
  window.setTimeout(function () {
    $(alert).alert('close');
  }, 4000);
  $('#alerts').append(alert);
}

ReactDOM.render(<DropzoneApp type="pdf" />, document.getElementById('pdfdropzone'));
ReactDOM.render(<DropzoneApp type="zip" />, document.getElementById('zipdropzone'));
ReactDOM.render(<Navigation />, document.getElementById('navigation'));