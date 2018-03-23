<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table cellpadding="5" cellspacing="0" border="1" class="tb" bordercolor="#bdbcbc">
	<tr>
		<td>
		Oracle
		</td>
		<td>
		VARCHAR2、NVARCHAR2类型， 字段长度必填。<br/>
		BLOB、CLOB、NCLOB类型，字段长度、默认值都不用填。<br/>
		NUMBER类型即可表示整数，又可以表示小数，在反向生成java代码时Integer、Double类型无法区分。故作以下约定，用于代码生成器。NUMBER 表示小数、NUMBER(11)表示整数，代码生成器根据NUMBER类型的长度进行判断，NUMBER生成Double类型，NUMBER(11)生成Integer类型。
		当然后期可以根据实际情况再手动调整。
		</td>
	</tr>
	<tr>
		<td>
		Mysql
		</td>
		<td>
		VARCHAR类型， 字段长度必填。<br/>
		TEXT类型，字段长度、默认值都不用填。<br/>
		DOUBLE类型，字段长度不用填。
		</td>
	</tr>
</table>