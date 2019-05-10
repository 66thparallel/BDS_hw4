#!/bin/bash 
# 
#SBATCH --nodes=1 
#SBATCH --ntasks-per-node=1 
#SBATCH --cpus-per-task=2 
#SBATCH --time=0:20:00 
#SBATCH --mem=10GB 
#SBATCH --job-name=JaneLiu_Homework4 
#SBATCH --mail-type=END 
#SBATCH --mail-user=[email address] 
#SBATCH --output=slurm_%j.out 
 
module purge 
module load jdk/1.8.0_111 
 
cd /scratch/[net ID]/hw4/ 
java -cp hw4.jar:./lib/* hw4.ClassifyTextualData



