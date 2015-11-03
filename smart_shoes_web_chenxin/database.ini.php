<?php
define ('DB_TYPE', 'mysql');
define ('DB_HOST', 'localhost');
define ('DB_USER', 'root');
define ('DB_PWD', '');
define ('DB_NAME', 'user_of_shoes');
define ('DB_CHARSET', 'utf8');
?>

<?php
/**
* db_mysqli
*/
class db_mysqli
{
	public $debug = false;
	private $version = "";
	private $db_conn = NULL;

	function __construct()
	{
		$this->debug = false;
	}

	public function connect($dbhost, $dbuser, $dbpwd, $dbname)
	{
		$this->db_conn = new mysqli($dbhost, $dbuser, $dbpwd, $dbname);
		if (mysqli_connect_errno()) {
			echo 'Could not connect to the database.';
			exit;
		}
	}

	public function query($sql)
	{
		if ($this->debug) {
			echo "hehe";//"<pre><hr>\n".$sql."\n<hr></pre>";
		}

		if (!($query = $this->db_conn->query($sql))) {
			$this->ErrorMsg();
			return false;
		} else {
			return $query;
		}
	}

	public function getOneRow($sql)
	{
		$res = $this->query($sql);
		if ($res !== false) {
			return mysqli_fetch_assoc($res);
		} else {
			return false;
		}
	}

	public function getRowsNum($sql)
	{
		$query = $this->query($sql);
		return mysqli_num_rows($query);
	}

	private function ErrorMsg($value='')
	{
		if ($value) {
			echo $value;
		} else {
			echo @mysqli_error();
		}
		exit();
	}
}

?>
<?php
$db = new db_mysqli();
$db->connect(DB_HOST, DB_USER, DB_PWD, DB_NAME);
if (function_exists('date_default_timezone_set')) {
	date_default_timezone_set('PRC');
}
?>
