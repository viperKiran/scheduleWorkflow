package scheduleWorkflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.protobuf.ServiceException;


class JavaThread extends Thread
{
	String arguments[];
	String className;
	Class javaClass;
	JavaThread(String className, String arguments[]) throws ClassNotFoundException
	{
		this.arguments=arguments;
		this.className=className;
		javaClass=Class.forName(className);
	}
	public void run()
	{
		try {
		      java.lang.reflect.Method mainMethod = javaClass.getDeclaredMethod("main", String[].class);
		       // Make sure that the method is public and static
		      int modifiers = mainMethod.getModifiers();
		      if ( !( Modifier.isPublic( modifiers ) && Modifier.isStatic( modifiers ) ) ) {
		        throw new ServiceException("The main method in class " + this.className  + " must be declared public and static.");
		      }
		     // Build the application args array
		      mainMethod.invoke(null, new Object[] { arguments });

		    } catch (Exception e) {
		      e.printStackTrace();;
		    }
	}
}
class WorkflowLookup
{
	Class<? extends Mapper> mapperClass;
	Class<? extends Reducer> reducerClass;
	Class<? extends Reducer> combinerClass;
	Class<?> jobClass;
	Class<?> outputKeyClass;
	Class<?> outputValueClass;
	String inputPaths[]=new String[100];
	String predecessor[]=new String[1000];
	String successor[]=new String[1000];
	String outputPath;
	String status;
	String jobName;
	String jobType;
	String seq2SparseWeight;
	String testOutput;
	String trainingOutput;
	String randomSelectionPct;
	String xm;
	String labelIndexPath;
	String MAHOUT_HOME;
	String modelPath;
	String describe;
	String javaClassName,arguments[];
	Thread thread;
	URI url;
	boolean afterJobCompletion;
	Job clientJob;
	Process process;
	int numTrees,selectedAttribute;
	String describePath,vectorClass,iterations,kPoints,distanceMeasure,clusterPath;
	int inputPathsCount,predecessorCount,successorCount;
	@SuppressWarnings("deprecation")
	WorkflowLookup()throws IOException, URISyntaxException
	{
		process=null;
		url=new URI("hdfs://localhost:9000");
		afterJobCompletion=true;
		MAHOUT_HOME=System.getenv("MAHOUT_HOME");
		inputPathsCount=0;predecessorCount=0;successorCount=0;status="Initialize";clientJob=new Job(new Configuration());
	}
	void setModelPath(String val)
	{
		this.modelPath=val;
	}
	String getModelPath()
	{
		return this.modelPath;
	}
	void setLabelIndexPath(String val)
	{
		this.labelIndexPath=val;
	}
	String getLabelIndexPath()
	{
		return this.labelIndexPath;
	}
	void setTestOutput(String val)
	{
		this.testOutput=val;
	}
	String getTestOutput()
	{
		return this.testOutput;
	}
	void setTrainingOutput(String val)
	{
		this.trainingOutput=val;
	}
	String getTrainingOutput()
	{
		return this.trainingOutput;
	}
	void setRandomSelectionPct(String val)
	{
		this.randomSelectionPct=val;
	}
	String getRandomSelectionPct()
	{
		return this.randomSelectionPct;
	}
	void setXm(String val)
	{
		this.xm=val;
	}
	String getXm()
	{
		return this.xm;
	}
	void setseq2SparseWeight(String val)
	{
		this.seq2SparseWeight=val;
	}
	String getSeq2SparseWeight()
	{
		return this.seq2SparseWeight;
	}
	int getPredecessorCount()
	{
		return this.predecessorCount;
	}
	int getSuccessorCount()
	{
		return this.successorCount;
	}
	int getInputPathsCount()
	{
		return this.inputPathsCount;
	}
	void setPredecessor(String val)
	{
		this.predecessor[this.predecessorCount++]=val;
	}
	String getPredecessor(int val)
	{
		return this.predecessor[val];
	}
	String getSuccessor(int val)
	{
		return this.successor[val];
	}
	void setSuccessor(String val)
	{
		this.successor[this.successorCount++]=val;
	}
	void setJobName(String val)
	{
		this.jobName=val;
	}
	String getJobName()
	{
		return this.jobName;
	}
	void setInputPaths(String val)
	{
		this.inputPaths[inputPathsCount++]=val;
	}
	String getInputPaths(int val)
	{
		return this.inputPaths[val];
	}
	void setOutputPath(String val)
	{
		this.outputPath=val;
	}
	String getOutputPath()
	{
		return this.outputPath;
	}
	void setJobClass(String val) throws ClassNotFoundException
	{
		this.jobClass=Class.forName(val);
	}
	Class<?> getJobClass()
	{
		return this.jobClass;
	}
	void setOutputKeyClass(String val) throws ClassNotFoundException
	{
		this.outputKeyClass=Class.forName(val);
	}
	Class<?> getOutputKeyClass()
	{
		return this.outputKeyClass;
	}
	void setOutputValueClass(String val) throws ClassNotFoundException
	{
		this.outputValueClass=Class.forName(val);
	}
	Class<?> getOutputvalueClass()
	{
		return this.outputValueClass;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void setMapperClass(String val) throws ClassNotFoundException
	{
		this.mapperClass=(Class<? extends Mapper>)Class.forName(val);
	}
	@SuppressWarnings("rawtypes")
	Class<? extends Mapper> getMapperClass()
	{
		return this.mapperClass;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void setCombinerClass(String val) throws ClassNotFoundException
	{
		this.combinerClass=(Class<? extends Reducer>)Class.forName(val);
	}
	@SuppressWarnings("rawtypes")
	Class<? extends Reducer> getCombinerClass()
	{
		return this.combinerClass;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	void setReducerClass(String val) throws ClassNotFoundException
	{
		this.reducerClass=(Class<? extends Reducer>)Class.forName(val);
	}
	@SuppressWarnings("rawtypes")
	Class<? extends Reducer> getReducerClass()
	{
		return this.reducerClass;
	}
	void setJobStatus(String val)
	{
		this.status=val;
	}
	String getJobStatus()
	{
		return this.status;
	}
	void setJobType(String val)
	{
		this.jobType=val;
	}
	String getJobType()
	{
		return this.jobType;
	}
	void setDescribe(String val)
	{
		this.describe=val;
	}
	String getDescribe()
	{
		return this.describe;
	}
	void setDescribePath(String val)
	{
		this.describePath=val;
	}
	String getDescribePath()
	{
		return this.describePath;
	}
	void setNumTrees(String val)
	{
		this.numTrees=Integer.parseInt(val);
	}
	int getNumTrees()
	{
		return this.numTrees;
	}
	void setSelectedAttribute(String val)
	{
		this.selectedAttribute=Integer.parseInt(val);
	}
	int getSelectedAttribute()
	{
		return this.selectedAttribute;
	}
	void setVectorClass(String val)
	{
		this.vectorClass=val;
	}
	String getVectorClass()
	{
		return this.vectorClass;
	}
	void setDistanceMeasure(String val)
	{
		this.distanceMeasure=val;
	}
	String getDistanceMeasure()
	{
		return this.distanceMeasure;
	}
	void setKPoints(String val)
	{
		this.kPoints=val;
	}
	String getKPoints()
	{
		return this.kPoints;
	}
	void setClusterPath(String val)
	{
		this.clusterPath=val;
	}
	String getClusterPath()
	{
		return this.clusterPath;
	}
	void setIterations(String val)
	{
		this.iterations=val;
	}
	String getIterations()
	{
		return this.iterations;
	}
	boolean isRunning(Process process)throws Exception {
    try {
       int exitValue=process.exitValue();
       if(exitValue!=0)
       {
       	System.out.println("Abnormal Termination");
       	BufferedReader br=new BufferedReader(new InputStreamReader(process.getErrorStream()));
       	String s;
       	while((s=br.readLine())!=null)
       		System.out.println(s);
       }
        return false;
    } catch (Exception e) {
        return true;
    }
	}
	void submitJob() throws IOException, ClassNotFoundException, InterruptedException
	{
		if(this.jobType.equals("JavaClass"))
		{
			this.setJobStatus("Submitted");
			thread=new JavaThread(this.javaClassName,this.arguments);
			thread.start();
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutKMeansCluster"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout kmeans -i "+this.getInputPaths(0)+" -o "+this.getOutputPath()+" -dm "+this.getDistanceMeasure()+" -x "+this.getIterations()+" -k "+this.getKPoints()+" -ow --clustering -c "+this.getClusterPath());
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}

		else if(this.jobType.equals("MahoutClusterInputConversion"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout org.apache.mahout.clustering.conversion.InputDriver -i "+this.getInputPaths(0)+" -o "+this.getOutputPath()+" -v "+this.getVectorClass());
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}

		else if(this.jobType.equals("MahoutTestForest"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout testforest -i "+this.getInputPaths(0)+" -ds "+this.getDescribePath()+" -m "+this.getModelPath()+" -a -mr -o "+this.getOutputPath());
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutBuildForest"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout buildforest -d "+this.getInputPaths(0)+" -ds "+this.getDescribePath()+" -sl "+this.getSelectedAttribute()+" -p -t "+this.getNumTrees()+" -o "+this.getOutputPath());
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutRandomForestDescribe"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout describe -p "+this.getInputPaths(0)+" -f "+this.getOutputPath()+" -d "+this.getDescribe());
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutSeqDumper"))
		{
			this.setJobStatus("Submitted");
			String filePathComponent[]=this.getOutputPath().split("/");
			String fileOutputPath=this.MAHOUT_HOME+"/"+filePathComponent[filePathComponent.length-1];
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout seqdumper -i "+this.getInputPaths(0)+" -o "+fileOutputPath);
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutTestNB"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout testnb -i "+this.getInputPaths(0)+" -o "+this.getOutputPath()+" -l "+this.getLabelIndexPath()+" -m "+this.getModelPath()+" -ow -c");
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutTrainNB"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout trainnb -i "+this.getInputPaths(0)+" -el -o "+this.getOutputPath()+" -li "+this.getLabelIndexPath()+" -ow -c");
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutSplit"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout split -i "+this.getInputPaths(0)+" --trainingOutput "+this.getTrainingOutput()+" --testOutput "+this.getTestOutput()+" --randomSelectionPct "+this.getRandomSelectionPct()+" --overwrite --sequenceFiles -xm "+this.getXm());
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutSeq2Sparse"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout seq2sparse -i "+this.getInputPaths(0)+" -o "+this.getOutputPath()+" -lnorm -nv -wt "+this.getSeq2SparseWeight());
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("MahoutSeqDirectory"))
		{
			this.setJobStatus("Submitted");
			//single input path - default it will be stroed in 0th index
			this.process = Runtime.getRuntime().exec(this.MAHOUT_HOME+"/bin/mahout seqdirectory -i "+this.getInputPaths(0)+" -o "+this.getOutputPath()+" -ow");
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
		else if(this.jobType.equals("HadoopMR"))
		{
			this.clientJob.setJobName(this.getJobName());
			this.clientJob.setJarByClass(this.getJobClass());
			this.clientJob.setOutputKeyClass(this.getOutputKeyClass());
			this.clientJob.setOutputValueClass(this.getOutputvalueClass());
			this.clientJob.setMapperClass(this.getMapperClass());
			this.clientJob.setCombinerClass(this.getCombinerClass());
			this.clientJob.setReducerClass(this.getReducerClass());
			String inputs="";
			for(int input=0;input<this.getInputPathsCount()-1;input++)
				inputs+=this.getInputPaths(input)+",";
			inputs+=this.getInputPaths(this.getInputPathsCount()-1);
			FileInputFormat.addInputPaths(this.clientJob, inputs);
			FileOutputFormat.setOutputPath(this.clientJob, new Path(this.getOutputPath()));
			this.setJobStatus("Submitted");
			this.clientJob.submit();
			System.out.println("Submitted "+this.getJobName()+" job to the YARN cluster");
		}
	}
	void parseXML(Node jobDetail) throws ClassNotFoundException, DOMException
	{
		if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("JavaClass"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.javaClassName = new String(jobElement.getElementsByTagName("javaClassName").item(0).getTextContent());
			NodeList argumentsList=jobElement.getElementsByTagName("arguments");
			Node argumentsListDetail=argumentsList.item(0);
			Element argumentsListElement=(Element) argumentsListDetail;
			NodeList argumentList=argumentsListElement.getElementsByTagName("argument");
			int count=0;
			arguments=new String[argumentList.getLength()];
			for(int argument=0;argument<argumentList.getLength();argument++)
			{
				Node argumentvalueDetail=argumentList.item(argument);
				Element argumentvalueElement=(Element) argumentvalueDetail;
				arguments[count++]=new String(argumentvalueElement.getElementsByTagName("value").item(0).getTextContent());
			}
			
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutKMeansCluster"))
		{
				Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			this.setDistanceMeasure(new String(jobElement.getElementsByTagName("distanceMeasure").item(0).getTextContent()));
			this.setKPoints(new String(jobElement.getElementsByTagName("kPoints").item(0).getTextContent()));
			this.setClusterPath(new String(jobElement.getElementsByTagName("clusterPath").item(0).getTextContent()));
			this.setIterations(new String(jobElement.getElementsByTagName("iterations").item(0).getTextContent()));

		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutClusterInputConversion"))
		{
				Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			this.setVectorClass(new String(jobElement.getElementsByTagName("vectorClass").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutTestForest"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			this.setDescribePath(new String(jobElement.getElementsByTagName("describePath").item(0).getTextContent()));
			this.setModelPath(new String(jobElement.getElementsByTagName("modelPath").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutBuildForest"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			this.setDescribePath(new String(jobElement.getElementsByTagName("describePath").item(0).getTextContent()));
			this.setNumTrees(new String(jobElement.getElementsByTagName("numTrees").item(0).getTextContent()));
			this.setSelectedAttribute(new String(jobElement.getElementsByTagName("selectedAttribute").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutRandomForestDescribe"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			this.setDescribe(new String(jobElement.getElementsByTagName("describe").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutSeqDumper"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutTestNB"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			this.setLabelIndexPath(new String(jobElement.getElementsByTagName("labelIndex").item(0).getTextContent()));
			this.setModelPath(new String(jobElement.getElementsByTagName("model").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutTrainNB"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			this.setLabelIndexPath(new String(jobElement.getElementsByTagName("labelIndex").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutSplit"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));	
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setTrainingOutput(new String(jobElement.getElementsByTagName("trainingOutput").item(0).getTextContent()));
			this.setTestOutput(new String(jobElement.getElementsByTagName("testOutput").item(0).getTextContent()));
			this.setRandomSelectionPct(new String(jobElement.getElementsByTagName("randomSelectionPct").item(0).getTextContent()));
			this.setXm(new String(jobElement.getElementsByTagName("xm").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutSeq2Sparse"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
			this.setseq2SparseWeight(new String(jobElement.getElementsByTagName("weight").item(0).getTextContent()));
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("MahoutSeqDirectory"))
		{
			Element jobElement=(Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			// Single input path - value will be stored in 0th index.
			this.setInputPaths(new String(jobElement.getElementsByTagName("input").item(0).getTextContent()));
			this.setOutputPath(new String(jobElement.getElementsByTagName("output").item(0).getTextContent()));
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
		}
		else if(jobDetail.getNodeType() == Node.ELEMENT_NODE && this.jobType.equals("HadoopMR"))
		{
			/* Parse XML and get the requirement value to run Hadoop jobs in the YARN cluster */
			Element jobElement= (Element) jobDetail;
			this.setJobName(new String(jobElement.getElementsByTagName("jobName").item(0).getTextContent()));
			this.setJobClass(new String(jobElement.getElementsByTagName("jobClass").item(0).getTextContent()));	
			this.setMapperClass(new String(jobElement.getElementsByTagName("mapperClass").item(0).getTextContent()));
			this.setReducerClass(new String(jobElement.getElementsByTagName("reducerClass").item(0).getTextContent()));
			this.setCombinerClass(new String(jobElement.getElementsByTagName("combinerClass").item(0).getTextContent()));
			this.setOutputKeyClass(new String(jobElement.getElementsByTagName("outputKeyClass").item(0).getTextContent()));
			this.setOutputValueClass(new String(jobElement.getElementsByTagName("outputValueClass").item(0).getTextContent()));
			NodeList inputPaths=jobElement.getElementsByTagName("inputPaths");
			Node inputPathsDetail=inputPaths.item(0);
			Element inputPathsElement=(Element) inputPathsDetail;
			NodeList inputPath=inputPathsElement.getElementsByTagName("input");
			for(int input=0;input<inputPath.getLength();input++)
			{
				Node inputPathDetail=inputPath.item(input);
				Element inputPathElement=(Element) inputPathDetail;
				this.setInputPaths(new String(inputPathElement.getElementsByTagName("path").item(0).getTextContent()));
			}
			this.setOutputPath(new String(jobElement.getElementsByTagName("outputPath").item(0).getTextContent()));
			String predecessorSet = new String(jobElement.getElementsByTagName("predecessor").item(0).getTextContent());
			predecessorSet=predecessorSet.trim();
			if(predecessorSet!=null && !predecessorSet.isEmpty())
			{
				String predecessors[]=predecessorSet.split(",");
				for(int predecessor=0;predecessor<predecessors.length;predecessor++)
					this.setPredecessor(predecessors[predecessor]);
			}
		}
	}
	boolean isJobComplete() throws Exception
	{
		//System.out.println(this.getJobName());
		if(this.jobType.equals("JavaClass") && !this.getJobStatus().equals("Initialize"))
		{
			//System.out.println(JavaClassName);
			if(this.thread.isAlive())
				return false;
			else
				return true;
		}
		else if(this.jobType.equals("MahoutKMeansCluster") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}	
		else if(this.jobType.equals("MahoutClusterInputConversion") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}	
		else if(this.jobType.equals("MahoutTestForest") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}
		else if(this.jobType.equals("MahoutBuildForest") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				//System.out.println("hi");
				return false;
			}
		else if(this.jobType.equals("MahoutRandomForestDescribe") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}
		else if(this.jobType.equals("MahoutSeqDumper") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}
		else if(this.jobType.equals("MahoutTestNB") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process)){
				return false;
		}
		else if(this.jobType.equals("MahoutTrainNB") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}
		else if(this.jobType.equals("MahoutSplit") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}
		else if(this.jobType.equals("MahoutSeq2Sparse") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}
		else if(this.jobType.equals("MahoutSeqDirectory") && !this.getJobStatus().equals("Initialize") && this.isRunning(this.process))
			{
				return false;
			}
		else if(this.jobType.equals("HadoopMR") && !this.getJobStatus().equals("Initialize") && !this.clientJob.isSuccessful())
			{
				return false;
			}
		return true;
	}
	void afterJob() throws Exception
	{
		if(this.afterJobCompletion)
		{
			if(this.jobType.equals("MahoutSeqDumper"))
			{
				String filePathComponent[]=this.getOutputPath().split("/");
				String localFilePath=this.MAHOUT_HOME+"/"+filePathComponent[filePathComponent.length-1];
				FileSystem hdfs =FileSystem.get(this.url,new Configuration());
				Path hdfsFilePath= new Path(this.getOutputPath());
				hdfs.copyFromLocalFile(new Path(localFilePath),hdfsFilePath);
				File file=new File(localFilePath);
				if(!file.delete())
					System.out.println("Problem in deleting a temporary file");
				hdfs.close();
				//Set afterJobCompletion false so it will not call again when we check for predecessors.
				this.afterJobCompletion=false;
			}
			else if(this.jobType.equals("MahoutTestForest"))
			{
				BufferedReader br=new BufferedReader(new InputStreamReader(this.process.getErrorStream()));
       			String s;
       			FileSystem hdfs =FileSystem.get(this.url,new Configuration());
       			Path hdfsFilePath= new Path(this.getOutputPath()+"/out.txt");;
       			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(hdfs.create(hdfsFilePath)));
       			while((s=br.readLine())!=null)
       				bw.write(s+"\n");
       			bw.close();
       			hdfs.close();
				this.afterJobCompletion=false;
			}
		}
	}
}
