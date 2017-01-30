
public class WarehouseOptimization {

	static boolean UNonVide(int[] U) // savoir si U est vide
	{
		for (int k=0; k<U.length; k++){
			if(U[k]!=-1) return true;
		}
	return false;
	}
	public static int[] convertIntToBinary(int value, int nbreDepot){
		String reBin = Integer.toBinaryString(value);
		int[] compteur= new int[nbreDepot];
		for (int j=0; j< nbreDepot; j++)
        {
            compteur[j] = 0;
        }
		int r=nbreDepot-reBin.length();
		for (int j=r; j<nbreDepot; j++)
        {
            compteur[j] = Character.getNumericValue(reBin.charAt(j-r));
        }
		return compteur;
	}
	
public static void main(String[] args) {
	int nbreDepot=3;
	int nbreDetaillant=8;
	int n= nbreDetaillant;
	int m= nbreDepot; // cette variable sera changeante au fur et à mesure 
	int limite=0;
	for(int k=0; k<nbreDepot; k++) limite+=Math.pow(2,k);
	
	int[][] cost={{27,12,12,16,24,31,41,13},
			{14,5,37,9,36,25,1,34},
			{34,34,20,9,19,19,3,34}};
	int[][] wPrime={{21,13,9,5,7,15,5,24},
			{20,8,18,25,6,6,9,6},
			{16,16,18,24,11,11,16,18}};
	int[] cPrime={100,100,140};
	int[] I={1,1,1};// investissement
	// pour la comparaison:Z et Y qu'on ititialise avec une valeur maximale du coût global
	int Z=0;
	int[] Y=new int[n];

	int max=0;
	for (int k=0; k<m; k++){
		for (int l=0; l<n; l++){
			Z+=cost[k][l];
			if(max<cost[k][l]) max=cost[k][l];
		}
		Z+=I[k];
	}
	int[][] pPrime =new int[m][n];
	int[][] fPrime =new int[m][n];
	for (int k=0; k<m; k++){
		for (int l=0; l<n; l++){
			fPrime[k][l]=-wPrime[k][l];
			pPrime[k][l]= max+1-cost[k][l];
		}
	}
	int[][] f=new int[m][n];
	int[][] p= new int[m][n];
	int[][] w=new int[m][n];
	int[] c=new int[m];
	for (int k=0; k<m; k++){
		for (int l=0; l<n; l++){
			p[k][l]=0;
			w[k][l]=0;
			f[k][l]=0;
		}
	}
	
	int[] compteur;
	
	//ce dont j'ai besoin pour l'algorithme intégré
	int maximum=-1000;
	int maximum2=-1000;
	int[] M=new int[m];// à initialiser dans la boucle
	int[] U=new int[n];// de même
	for (int k=1; k<=n; k++){
		U[k-1]=k;
}
	int FjNul=-1;
	int FjNulSansI=-1;
	boolean feas=true;
	int[] cbar=c;
	int zh=0;
	int d=-1000;
	int iPrime = 0;
	int iSeconde=-1;
	int dEtoile=0;
	int iEtoile = 0;
	int jEtoile = 0;
	int[] y=new int[n];
	int maxA=0;
	
	//place à l'attaque
			for(int pas=1; pas<=limite; pas++)
			{ 	m=0;
				compteur= convertIntToBinary(pas, nbreDepot);
				System.out.print("\ncompteur ");
				for (int k =0; k<= compteur.length-1; k++)
				{
				System.out.print(compteur[k]);
		        }
				System.out.print(" et pas"+pas);
				for(int k=1; k<=nbreDepot; k++)
				{
				if(compteur[k-1]==1) 
					{ m++; // System.out.print(" m="+m);
				for(int j=1; j<=nbreDetaillant; j++)
						{
					p[m-1][j-1]=pPrime[k-1][j-1];
					w[m-1][j-1]=wPrime[k-1][j-1];
					f[m-1][j-1]=-w[m-1][j-1];
					c[m-1]=cPrime[k-1];
					//System.out.print(" "+p[m-1][j-1]);
						}
					}
				}
				for (int k=1; k<=m; k++){
					M[k-1]=k;
				}
				for (int k=1; k<=n; k++){
					U[k-1]=k;
				}
				FjNul=-1;
				FjNulSansI=-1;
				feas=true;
				cbar=c;
				zh=0;
				d=-1000;
				iPrime = 0;
				iSeconde=-1;
				dEtoile=0;
				iEtoile = 0;
				jEtoile = 0;
				maxA=0;
				while(UNonVide(U)==true && feas==true )
				{
					d=-1000;
					dEtoile=-1000;
					for(int j=1; j<=n; j++)
						{ if(U[j-1]!=-1)  // cad  for each j de U
						  {
							FjNul=-1; // pour le feas false
						for (int i=1; i<=m; i++){ 
								if(w[i-1][j-1]<=cbar[i-1]) { /*F[i-1][j-1]=i;*/ FjNul=0;}
						}
							if(FjNul==-1) {feas=false; System.out.print(" n'est pas faisable ");}
							else{
								FjNulSansI=-1;
								maximum=-1000;
								maximum2=-1000;
								for (int i=1; i<=m; i++){ 
									if(maximum<f[i-1][j-1] && w[i-1][j-1]<=cbar[i-1]) { iPrime=i; maximum2=maximum; maximum=f[i-1][j-1];}
							}
								for (int i=1; i<=m; i++){ 
									if(maximum2<f[i-1][j-1] && f[i-1][j-1]<maximum && w[i-1][j-1]<=cbar[i-1])  maximum2=f[i-1][j-1];
							}
								for (int i=1; i<=m; i++){ 
									if(i!=iPrime && w[i-1][j-1]<=cbar[i-1]) FjNulSansI=0; 
							}
								
								if(FjNulSansI==-1) d=+1000;
								else d=maximum-maximum2;
								if(d>dEtoile)
								{ dEtoile=d;
								  iEtoile=iPrime;
								  jEtoile=j;
								}	
							}
							}
						}
						if (feas==true){
							y[jEtoile-1]=iEtoile;
							zh+=p[iEtoile-1][jEtoile-1];
							cbar[iEtoile-1]-=w[iEtoile-1][jEtoile-1];
							U[jEtoile-1]=-1;
						}		}
				if (feas==true)
				{
					for(int j=1; j<=n;j++)
					{
						iPrime=y[j-1];
						iSeconde=-1;
						for(int i=1; i<=m; i++)
						{
							if(maxA<p[i-1][j-1] && w[i-1][j-1]<=cbar[i-1] && i!=iPrime){ 
								maxA=p[i-1][j-1];
								iSeconde=i;
							}	
						}
						if(iSeconde!=-1){
							if(p[iSeconde-1][j-1]>p[iPrime-1][j-1])
							{
								y[j-1]=iSeconde;
								zh=zh-p[iPrime-1][j-1]+p[iSeconde-1][j-1];
								cbar[iPrime-1]+=w[iPrime-1][j-1];
								cbar[iSeconde-1]-=w[iSeconde-1][j-1];
							}
						}	
					}
					zh=n*(max+1)-zh;
					for(int j=1;j<=compteur.length;j++) {
						if(compteur[j-1]==0){
							for(int k=1; k<=n; k++){if(y[k-1]>=j) y[k-1]=y[k-1]+1;}
							}
						else zh+=I[compteur[j-1]];
					}// pour ajuster les y de i j
					System.out.print("\n");
					for(int j=1;j<=n;j++) {
						System.out.print(""+y[j-1]);
					}
					//for(int k=1; k<=n; k++) zh+=I[y[k-1]-1];
					System.out.println("\n zh="+zh);
					if(Z>zh) {
						Z=zh;
						for(int j=1;j<=n;j++) Y[j-1]=y[j-1];	
					}
				}
			}
			System.out.print("\n Z="+Z+" et Y=");
			for(int j=1;j<=n;j++) System.out.print(Y[j-1]);
}
}


	
		
		
	
